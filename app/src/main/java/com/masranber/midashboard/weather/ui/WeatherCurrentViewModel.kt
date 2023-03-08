package com.masranber.midashboard.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Priority
import com.masranber.midashboard.App
import com.masranber.midashboard.StatefulViewModel
import com.masranber.midashboard.domain.Resource
import com.masranber.midashboard.location.GeocodingProvider
import com.masranber.midashboard.location.LocationProvider
import com.masranber.midashboard.location.mapStateToAbbreviation
import com.masranber.midashboard.weather.domain.WeatherCurrent
import com.masranber.midashboard.widgets.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class WeatherCurrentViewModel : StatefulViewModel<WeatherCurrentViewModel.State>() {

    data class State(
        val location: String,
        val weather: WeatherCurrent?,
        val weatherIcon: Int?,
        val currentAlert: Int,
        val error: String?
    )

    private val locationProvider: LocationProvider by lazy { LocationProvider(App.getInstance().applicationContext) }
    private val geocodingProvider: GeocodingProvider by lazy { GeocodingProvider(App.getInstance().applicationContext) }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepository.getCurrentWeather(
                locationDelegate = {
                    val locationRes = locationProvider.getDeviceLocation(Priority.PRIORITY_LOW_POWER)
                    locationRes.error?.let { /* handle get location error */ }
                    locationRes.data // last statement is implicit return from lambda
                },
                interval = 1.toDuration(DurationUnit.MINUTES)
            )
                .distinctUntilChanged() // ignore if weather data hasn't changed since last interval
                .map { resource ->
                    resource.data?.let { weather ->
                        val address = geocodingProvider.getAddressForLocation(weather.lat, weather.lon)
                        address.data?.let {
                            return@map Resource.Success(weather.copy(locationString = "${it.locality}, ${mapStateToAbbreviation(it.adminArea)}"))
                        }
                    }
                    return@map resource
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it.data?.let {
                        val icon = if(it.current.time < it.daily[0].sunsetTime || it.current.time < it.daily[0].sunriseTime) it.current.conditions[0].icon else it.current.conditions[0].iconAlt
                        mutableState.value = mutableState.value.copy(location = it.locationString ?: "Current Location", weather = it.current, weatherIcon = icon as Int)
                    }
                    it.error?.let {
                        mutableState.value = mutableState.value.copy(error = "Unable to fetch weather for current location")
                    }
                }
        }
    }

    override fun getInitialState(): State {
        return State(location = "N/A", weather = null, weatherIcon = null, currentAlert = 0, error = "Waiting for weather data")
    }
}

package com.masranber.midashboard.weather.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.masranber.midashboard.App
import com.masranber.midashboard.StatefulViewModel
import com.masranber.midashboard.location.LocationProvider
import com.masranber.midashboard.weather.domain.WeatherAlert
import com.masranber.midashboard.widgets.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class WeatherAlertsViewModel : StatefulViewModel<WeatherAlertsViewModel.State>() {

    companion object {
        const val errorStringNoAlerts = "There are no weather alerts currently."
    }

    data class State(val currentAlert: Int, val alerts: List<WeatherAlert>, val error: String?)

    private val locationProvider: LocationProvider by lazy { LocationProvider(App.getInstance().applicationContext) }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepository.getWeatherAlerts(
                locationDelegate = {
                    val locationRes = locationProvider.getDeviceLastLocation()
                    locationRes.error?.let { /* handle get location error */ }
                    locationRes.data // last statement is implicit return from lambda
                },
                interval = 1.toDuration(DurationUnit.MINUTES)
            )
                .distinctUntilChanged() // ignore if weather data hasn't changed since last interval
                .flowOn(Dispatchers.IO)
                .collect {
                    it.data?.let {
                        if(it.isNotEmpty()) {
                            mutableState.value = mutableState.value.copy(alerts = it)
                        } else {
                            mutableState.value = mutableState.value.copy(error = errorStringNoAlerts)
                        }
                    }
                    it.error?.let {
                        // handle get weather alerts error
                    }
                }
        }

        viewModelScope.launch {
            while(isActive) {
                if(state.value.alerts.isNotEmpty()) {
                    val nextAlert = (state.value.currentAlert + 1) % state.value.alerts.size
                    mutableState.value = state.value.copy(currentAlert = nextAlert)
                } else {
                    mutableState.value = state.value.copy(currentAlert = 0)
                }
                Log.i("WeatherAlerts", "currentAlert = ${state.value.currentAlert}")
                delay(5_000) // show each alert for 5 seconds
            }
        }
    }


    override fun getInitialState(): State {
        return State(0, emptyList(), error = errorStringNoAlerts)
    }

}
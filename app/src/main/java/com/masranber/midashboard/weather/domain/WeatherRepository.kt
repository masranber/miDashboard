package com.masranber.midashboard.widgets.weather

import android.location.Location
import android.os.SystemClock
import android.util.Log
import com.masranber.midashboard.domain.Resource
import com.masranber.midashboard.news.data.NewsAPI
import com.masranber.midashboard.news.data.NewsAPIService
import com.masranber.midashboard.news.domain.toNewsArticles
import com.masranber.midashboard.weather.data.openweatherapi.OpenWeatherAPI
import com.masranber.midashboard.weather.data.openweatherapi.OpenWeatherDTO
import com.masranber.midashboard.weather.data.openweatherapi.OpenWeatherService
import com.masranber.midashboard.weather.domain.WeatherAlert
import com.masranber.midashboard.weather.domain.WeatherCondition
import com.masranber.midashboard.weather.domain.WeatherForecast
import com.masranber.midashboard.weather.domain.toWeatherForecast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

enum class WeatherError {
    NoLocation,
    NoWeatherAlerts,
    ResponseBodyMissing,
    UnableToReachService,
}

object WeatherRepository {

    var minApiInterval = OpenWeatherAPI.REFRESH_INTERVAL // milliseconds, must be >= 15 min
        set(value) {
            if(value >= OpenWeatherAPI.REFRESH_INTERVAL) field = value
        }

    private val updateMutex: Mutex = Mutex()

    private var lastApiCallTimestamp: Long = 0
    private var cachedWeather: WeatherForecast? = null

    fun getWeatherAlerts(locationDelegate: (suspend () -> Location?), interval: Duration) : Flow<Resource<List<WeatherAlert>, WeatherError>> {
        return flow {
            while (true) {
                val location = locationDelegate()
                location?.let { loc ->
                    val res = getCurrentWeather(loc.latitude, loc.longitude)
                    res.data?.alerts?.let { emit(Resource.Success(it)) } ?: res.error
                }
                delay(interval)
            }
        }
    }

    fun getCurrentWeather(locationDelegate: (suspend () -> Location?), interval: Duration) : Flow<Resource<WeatherForecast, WeatherError>> {
        return flow {
            while (true) {
                val location = locationDelegate()
                location?.let {
                    emit(getCurrentWeather(it.latitude, it.longitude))
                }
                delay(interval)
            }
        }
    }

    fun getCurrentWeather(lat: Double, lon: Double, interval: Duration) : Flow<Resource<WeatherForecast, WeatherError>> {
        return flow {
            while (true) {
                emit(getCurrentWeather(lat, lon))
                delay(interval)
            }
        }
    }

    suspend fun getCurrentWeather(lat: Double, lon: Double) : Resource<WeatherForecast, WeatherError> {
        updateMutex.withLock {
            if(isCacheStale() || cachedWeather == null) { // TODO load cached from database if null
                Log.i("WeatherRepository", "GET: weather by location")
                val response = OpenWeatherService.service.getWeather(lat, lon, OpenWeatherAPI.Units.METRIC, OpenWeatherAPI.API_KEY)
                lastApiCallTimestamp = SystemClock.elapsedRealtime()
                response.body()?.let {
                    Log.i("WeatherRepository", "GET: ${response.code()} ${it}")
                    cachedWeather = it.toWeatherForecast()
                    return Resource.Success(cachedWeather)
                }
                // TODO populate HTTP response codes
                Log.i("WeatherRepository", "ERROR: ${response.code()} (${response.message()})")
                return Resource.Error(when(response.code()) {
                    200 -> WeatherError.ResponseBodyMissing
                    else -> WeatherError.UnableToReachService
                })
            } else {
                Log.i("WeatherRepository", "GET: weather by location (cached)")
                return if(cachedWeather != null) Resource.Success(cachedWeather) else Resource.Error(WeatherError.UnableToReachService)
            }
        }
    }

    private fun isCacheStale() : Boolean {
        return SystemClock.elapsedRealtime() - lastApiCallTimestamp > minApiInterval;
    }
}
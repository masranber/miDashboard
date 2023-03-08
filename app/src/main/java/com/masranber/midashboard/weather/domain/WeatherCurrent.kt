package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.Length
import com.masranber.midashboard.util.units.Speed
import com.masranber.midashboard.util.units.Temperature
import java.time.ZonedDateTime

data class WeatherCurrent (
    val time: ZonedDateTime,
    val sunriseTime: ZonedDateTime,
    val sunsetTime: ZonedDateTime,
    val temp: Temperature,
    val feelsLike: Temperature,
    val tempForecast: TemperatureForecastDaily,
    val pressure: Int, // TODO add pressure units
    val humidity: Float,
    val dewPoint: Temperature,
    val uvIndex: Float,
    val cloudCover: Float,
    val visibility: Length,
    val windSpeed: Speed,
    val windDirection: Int,
    val windGustSpeed: Speed,
    val conditions: List<WeatherCondition>,
    val alerts: List<WeatherAlert>,
)
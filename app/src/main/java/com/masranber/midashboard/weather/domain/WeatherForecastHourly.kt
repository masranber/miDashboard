package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.Length
import com.masranber.midashboard.util.units.Speed
import com.masranber.midashboard.util.units.Temperature
import java.time.ZonedDateTime

data class WeatherForecastHourly (
    val time: ZonedDateTime,
    val temp: Temperature,
    val feelsLike: Temperature,
    val pressure: Int,
    val humidity: Float,
    val dewPoint: Temperature,
    val uvIndex: Float,
    val cloudCover: Float,
    val visibility: Length,
    val windSpeed: Speed,
    val windDirection: Int,
    val windGustSpeed: Speed,
    val precipitationChance: Float,
    val rain: Length,
    val snow: Length,
    val conditions: List<WeatherCondition>,
)

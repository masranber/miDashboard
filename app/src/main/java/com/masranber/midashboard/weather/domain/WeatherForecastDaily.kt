package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.Length
import com.masranber.midashboard.util.units.Speed
import com.masranber.midashboard.util.units.Temperature
import java.time.ZonedDateTime

data class WeatherForecastDaily(
    val time: ZonedDateTime,
    val sunriseTime: ZonedDateTime,
    val sunsetTime: ZonedDateTime,
    val moonriseTime: ZonedDateTime,
    val moonsetTime: ZonedDateTime,
    val moonPhase: Float,
    val temp: TemperatureForecastDaily,
    val feelsLike: TemperatureForecastDaily,
    val pressure: Int,
    val humidity: Float,
    val dewPoint: Temperature,
    val uvIndex: Float,
    val cloudCover: Float,
    val windSpeed: Speed,
    val windDirection: Int,
    val windGustSpeed: Speed,
    val precipitationChance: Float,
    val rain: Length,
    val snow: Length,
    val conditions: List<WeatherCondition>,
)

package com.masranber.midashboard.weather.domain

data class WeatherForecast(
    val lat: Double,
    val lon: Double,
    val locationString: String?,
    val current: WeatherCurrent,
    val minutely: List<WeatherForecastMinutely>,
    val hourly: List<WeatherForecastHourly>,
    val daily: List<WeatherForecastDaily>,
    val alerts: List<WeatherAlert>,
)

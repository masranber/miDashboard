package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.Temperature

data class TemperatureForecastDaily(
    val morning: Temperature,
    val day: Temperature,
    val evening: Temperature,
    val night: Temperature,
    val min: Temperature?,
    val max: Temperature?,
)

package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.Length
import java.time.ZonedDateTime

data class WeatherForecastMinutely (
    val time: ZonedDateTime,
    val precipitation: Length,
)

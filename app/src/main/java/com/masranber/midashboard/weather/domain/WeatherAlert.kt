package com.masranber.midashboard.weather.domain

import java.time.ZonedDateTime

data class WeatherAlert(
    val source: String,
    val severity: Severity,
    val what: String,
    val from: ZonedDateTime,
    val until: ZonedDateTime,
    val detailed: String,
    val tags: List<String>,
) {
    enum class Severity {
        ADVISORY, WATCH, WARNING
    }
}

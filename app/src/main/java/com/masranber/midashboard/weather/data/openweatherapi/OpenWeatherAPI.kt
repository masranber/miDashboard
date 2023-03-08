package com.masranber.midashboard.weather.data.openweatherapi

object OpenWeatherAPI {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "8c41f7cddfca7b3c25768e312551e558"
    const val ONECALL_END_POINT = "/data/3.0/onecall"

    const val REFRESH_INTERVAL = 900_000 // 15 min in milliseconds

    object Units {
        const val STANDARD = "standard"
        const val METRIC = "metric"
        const val IMPERIAL = "imperial"
    }
}
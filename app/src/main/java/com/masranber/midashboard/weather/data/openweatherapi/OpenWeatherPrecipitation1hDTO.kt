package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherPrecipitation1hDTO(
    @SerializedName("1h")
    val nextHour: Float
)

package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherMinutelyDTO(
    @SerializedName("dt")
    val time: Long,
    @SerializedName("precipitation")
    val precipitation: Float,
)

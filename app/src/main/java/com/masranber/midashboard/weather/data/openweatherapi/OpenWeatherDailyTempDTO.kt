package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherDailyTempDTO(
    @SerializedName("morn")
    val morning: Float,
    @SerializedName("day")
    val day: Float,
    @SerializedName("eve")
    val evening: Float,
    @SerializedName("night")
    val night: Float,
    @SerializedName("min")
    val min: Float?,
    @SerializedName("max")
    val max: Float?,
)

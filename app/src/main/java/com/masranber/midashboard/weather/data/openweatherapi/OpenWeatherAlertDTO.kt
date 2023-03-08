package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherAlertDTO(
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("start")
    val start: Long,
    @SerializedName("end")
    val end: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("tags")
    val tags: List<String>,
)

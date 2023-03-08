package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherDTO(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Long,
    @SerializedName("current")
    val current: OpenWeatherCurrentDTO,
    @SerializedName("minutely")
    val minutely: List<OpenWeatherMinutelyDTO>,
    @SerializedName("hourly")
    val hourly: List<OpenWeatherHourlyDTO>,
    @SerializedName("daily")
    val daily: List<OpenWeatherDailyDTO>,
    @SerializedName("alerts")
    val alerts: List<OpenWeatherAlertDTO>?,
)

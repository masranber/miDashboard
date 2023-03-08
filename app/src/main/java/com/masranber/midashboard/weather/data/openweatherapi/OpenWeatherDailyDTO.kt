package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherDailyDTO(
    @SerializedName("dt")
    val time: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("moonrise")
    val moonrise: Long,
    @SerializedName("moonset")
    val moonset: Long,
    @SerializedName("moon_phase")
    val moonPhase: Float,
    @SerializedName("temp")
    val temp: OpenWeatherDailyTempDTO,
    @SerializedName("feels_like")
    val feelsLike: OpenWeatherDailyTempDTO,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Float,
    @SerializedName("uvi")
    val uvIndex: Float,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDirection: Int,
    @SerializedName("wind_gust")
    val windGust: Float?,
    @SerializedName("pop")
    val pop: Float,
    @SerializedName("rain")
    val rain: Float?,
    @SerializedName("snow")
    val snow: Float?,
    @SerializedName("weather")
    val conditions: List<OpenWeatherConditionDTO>,
)

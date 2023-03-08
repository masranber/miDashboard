package com.masranber.midashboard.weather.data.openweatherapi

import com.google.gson.annotations.SerializedName

data class OpenWeatherCurrentDTO (
    @SerializedName("dt")
    val time: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
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
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDirection: Int,
    @SerializedName("wind_gust")
    val windGust: Float?,
    @SerializedName("rain")
    val rain: OpenWeatherPrecipitation1hDTO?,
    @SerializedName("snow")
    val snow: OpenWeatherPrecipitation1hDTO?,
    @SerializedName("weather")
    val conditions: List<OpenWeatherConditionDTO>,
)

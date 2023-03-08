package com.masranber.midashboard.weather.data.openweatherapi

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET(OpenWeatherAPI.ONECALL_END_POINT)
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") apiKey: String): Response<OpenWeatherDTO>

    companion object {
        val service : OpenWeatherService by lazy {
            Retrofit.Builder()
                .baseUrl(OpenWeatherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService::class.java)
        }
    }
}
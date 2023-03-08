package com.masranber.midashboard.news.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET(NewsAPI.TOP_HEADLINES_ENDPOINT)
    suspend fun getTopNewsByCountry(@Query("country") country: String, @Query("apiKey") apiKey: String): Response<NewsDTO>

    @GET(NewsAPI.TOP_HEADLINES_ENDPOINT)
    suspend fun getTopNewsBySource(@Query("sources") sources: String, @Query("apiKey") apiKey: String): Response<NewsDTO>

    companion object {
        val service : NewsAPIService by lazy {
            Retrofit.Builder()
                .baseUrl(NewsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIService::class.java)
        }
    }
}
package com.masranber.midashboard.news.data

object NewsAPI {
    const val BASE_URL: String = "https://newsapi.org"
    const val API_KEY: String = "b72b337f664e4eae985bb6aa88e08944"

    const val TOP_HEADLINES_ENDPOINT = "/v2/top-headlines"

    const val REFRESH_INTERVAL = 900_000 // 15 min in milliseconds
}
package com.masranber.midashboard.news.data

import com.google.gson.annotations.SerializedName

data class NewsDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<NewsArticleDTO>,
)

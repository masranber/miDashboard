package com.masranber.midashboard.news.data

import com.google.gson.annotations.SerializedName

data class NewsSourceDTO(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String,
)
package com.masranber.midashboard.news.domain

data class NewsSource(val id: String, val name: String) {
    companion object{
        val AssociatedPress = NewsSource(
            id = "associated-press",
            name = "Associated Press"
        )
    }
}
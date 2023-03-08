package com.masranber.midashboard.news.domain

import java.time.ZonedDateTime

data class NewsArticle(
    val source: NewsSource,
    val author: String,
    val title: String,
    val summary: String,
    val fullArticleUrl: String,
    val imageUrl: String,
    val publishedAt: ZonedDateTime,
)

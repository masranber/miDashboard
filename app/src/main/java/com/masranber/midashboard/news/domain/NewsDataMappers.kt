package com.masranber.midashboard.news.domain

import com.masranber.midashboard.news.data.NewsArticleDTO
import com.masranber.midashboard.news.data.NewsDTO
import com.masranber.midashboard.news.data.NewsSourceDTO
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun NewsDTO.toNewsArticles() : List<NewsArticle> {
    return this.articles.map { it.toNewsArticle() }
}

fun NewsArticleDTO.toNewsArticle() = NewsArticle(
    source = this.source.toNewsSource(),
    author = this.author ?: "",
    title = this.title,
    summary = this.description ?: "",
    fullArticleUrl = this.url,
    imageUrl = this.urlToImage ?: "",
    publishedAt = ZonedDateTime.parse(this.publishedAt, DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())),
)

fun NewsSourceDTO.toNewsSource() = NewsSource(
    id = this.id ?: "",
    name = this.name
)
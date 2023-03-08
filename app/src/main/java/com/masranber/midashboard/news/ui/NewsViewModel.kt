package com.masranber.midashboard.news.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masranber.midashboard.news.domain.NewsArticle
import com.masranber.midashboard.news.domain.NewsSource
import com.masranber.midashboard.widgets.weather.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class NewsViewModel : ViewModel() {

    data class State(val newsArticles: List<NewsArticle>, val currentArticle: Int)

    private val _newsArticles: MutableStateFlow<State> = MutableStateFlow(State(emptyList(), 0))
    val newsArticles: StateFlow<State> = _newsArticles

    init {
        NewsRepository.minApiInterval = 1_800_000 // 30 minutes between news fetches
        viewModelScope.launch {
            while(isActive) {
                if(_newsArticles.value.newsArticles.size > 0) {
                    val nextArticle = (_newsArticles.value.currentArticle + 1) % _newsArticles.value.newsArticles.size
                    _newsArticles.value = _newsArticles.value.copy(currentArticle = nextArticle)
                }
                if(_newsArticles.value.currentArticle == 0) {
                    refreshNewsArticles() // only refresh article list when wrapping back to beginning to avoid jarring UI
                }
                delay(30_000) // show each article for 30 seconds
            }
        }
    }

    private suspend fun refreshNewsArticles(currentArticle: Int = 0) {
        val response = NewsRepository.getTopNewsBySource(listOf(NewsSource.AssociatedPress))
        response.data?.let {
            _newsArticles.value = _newsArticles.value.copy(newsArticles = it, currentArticle = currentArticle)
        }
        response.error?.let {
            Log.i("NewsViewModel", "error refreshing news articles: ${it.name}")
            // TODO handle news fetch error
        }
    }

    fun setCurrentArticle(index: Int) {
        _newsArticles.value = _newsArticles.value.copy(currentArticle = index)
    }

}
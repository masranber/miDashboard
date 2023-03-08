package com.masranber.midashboard.widgets.weather

import android.os.SystemClock
import android.util.Log
import com.masranber.midashboard.domain.Resource
import com.masranber.midashboard.news.data.NewsAPI
import com.masranber.midashboard.news.data.NewsAPIService
import com.masranber.midashboard.news.data.NewsDTO
import com.masranber.midashboard.news.domain.NewsArticle
import com.masranber.midashboard.news.domain.NewsSource
import com.masranber.midashboard.news.domain.toNewsArticles
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration

enum class NewsError {
    ResponseBodyMissing,
    UnableToReachService,
}

object NewsRepository {

    var minApiInterval = NewsAPI.REFRESH_INTERVAL // milliseconds, must be >= 15 min
        set(value) {
            if(value >= NewsAPI.REFRESH_INTERVAL) field = value
        }

    private val updateMutex: Mutex = Mutex() // thread-safe refresh interval

    private var lastApiCallTimestamp: Long = 0
    private var cachedNews: List<NewsArticle>? = null

    fun getTopNewsBySource(sources: List<NewsSource>, interval: Duration) : Flow<Resource<List<NewsArticle>, NewsError>> {
        return flow {
            while(true) {
                emit(getTopNewsBySource(sources))
                delay(interval)
            }
        }
    }

    suspend fun getTopNewsBySource(sources: List<NewsSource>) : Resource<List<NewsArticle>, NewsError> {
        updateMutex.withLock {
            if(isCacheStale() || cachedNews == null) {
                val sourcesStr = sources.joinToString { it.id }
                Log.i("NewsRepository", "GET: top news by source: ${sourcesStr}")
                val response = NewsAPIService.service.getTopNewsBySource(
                    sourcesStr,
                    NewsAPI.API_KEY
                )
                lastApiCallTimestamp = SystemClock.elapsedRealtime()
                response.body()?.let {
                    Log.i("NewsRepository", "GET: ${response.code()} (${it})")
                    cachedNews = it.toNewsArticles()
                    return Resource.Success(cachedNews)
                }
                // TODO populate HTTP response codes
                Log.i("NewsRepository", "ERROR: ${response.code()} (${response.message()})")
                return Resource.Error(when(response.code()) {
                    200 -> NewsError.ResponseBodyMissing
                    else -> NewsError.UnableToReachService
                })
            } else {
                Log.i("NewsRepository", "GET: top news by source (cached)")
                return if(cachedNews != null) Resource.Success(cachedNews) else Resource.Error(NewsError.UnableToReachService)
            }
        }
    }

    private fun isCacheStale() : Boolean {
        return SystemClock.elapsedRealtime() - lastApiCallTimestamp >= minApiInterval;
    }
}
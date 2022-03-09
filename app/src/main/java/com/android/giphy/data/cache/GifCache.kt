package com.android.giphy.data.cache

import com.android.giphy.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifCache {

    val gifFlow: Flow<List<Gif>>

    suspend fun addToCache(gifs: List<Gif>)

    fun getGifById(id: String): Gif?

    fun isCacheEmpty(): Boolean
}
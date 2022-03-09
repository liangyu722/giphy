package com.android.giphy.data.cache

import com.android.giphy.model.Gif
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultGifCache(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GifCache {

    private val cachedGifs: ConcurrentMap<String, Gif> = ConcurrentHashMap()
    private val _gifFlow = MutableStateFlow<List<Gif>>(emptyList())
    override val gifFlow: Flow<List<Gif>> = _gifFlow

    override suspend fun addToCache(gifs: List<Gif>) {
        withContext(dispatcher) {
            gifs.forEach {
                cachedGifs[it.id] = it
            }
            _gifFlow.emit(cachedGifs.values.toList())
        }
    }

    override fun getGifById(id: String): Gif? {
        return cachedGifs[id]
    }

    override fun isCacheEmpty(): Boolean {
        return cachedGifs.isEmpty()
    }
}
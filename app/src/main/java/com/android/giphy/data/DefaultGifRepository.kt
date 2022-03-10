package com.android.giphy.data

import androidx.paging.*
import com.android.giphy.common.RepositoryLoadingException
import com.android.giphy.common.Result
import com.android.giphy.data.cache.GifCache
import com.android.giphy.data.common.toGifs
import com.android.giphy.data.networking.GiphyService
import com.android.giphy.model.Gif
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultGifRepository(
    private val service: GiphyService,
    private val cache: GifCache,
    private val dispatcher: CoroutineDispatcher,
    private val pagingSource: GifPagingSource
) : GifRepository {

    override val gifFlow = cache.gifFlow

    override val pagingDataFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            pagingSource
        }
    ).flow.map {
        it.map { data ->
            data.toGifs()
        }
    }

    override suspend fun getGifs(): Result<Unit> {
        return withContext(dispatcher) {
            if (cache.isCacheEmpty()) {
                val gifs = fetchGifs()
                if (gifs is Result.Error) {
                    return@withContext Result.Error(RepositoryLoadingException("Cannot load gifs from server"))
                }
                cache.addToCache((gifs as Result.Success).data)
            }
            return@withContext Result.Success(Unit)
        }
    }

    private suspend fun fetchGifs(): Result<List<Gif>> {
        return try {
            val result = service.getTrendingGifs(0).toGifs()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
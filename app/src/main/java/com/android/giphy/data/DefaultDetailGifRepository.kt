package com.android.giphy.data

import com.android.giphy.common.RepositoryLoadingException
import com.android.giphy.common.Result
import com.android.giphy.data.cache.DefaultGifCache
import com.android.giphy.data.cache.GifCache
import com.android.giphy.model.Gif
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultDetailGifRepository(
    private val cache: GifCache,
    private val dispatcher: CoroutineDispatcher
) : DetailGifRepository {

    override suspend fun viewGif(id: String): Result<Gif> {
        return withContext(dispatcher) {
            val gif = cache.getGifById(id)
            return@withContext if (gif != null) {
                Result.Success(gif)
            } else {
                Result.Error(RepositoryLoadingException("Unable to load gif from cache"))
            }
        }
    }
}
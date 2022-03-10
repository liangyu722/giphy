package com.android.giphy.di

import androidx.paging.PagingSource
import com.android.giphy.data.*
import com.android.giphy.data.cache.DefaultGifCache
import com.android.giphy.data.cache.GifCache
import com.android.giphy.data.networking.GiphyService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesGifCache(): GifCache {
        return DefaultGifCache(Dispatchers.IO)
    }

    @Provides
    fun providesGiphyRepository(
        service: GiphyService,
        cache: GifCache,
        pagingSource: GifPagingSource
    ): GifRepository {
        return DefaultGifRepository(service, cache, Dispatchers.IO, pagingSource)
    }

    @Provides
    fun providesDetailGiphyRepository(cache: GifCache): DetailGifRepository {
        return DefaultDetailGifRepository(cache, Dispatchers.IO)
    }

    @Provides
    fun providesPagingSource(service: GiphyService): GifPagingSource {
        return GifPagingSource(service)
    }
}
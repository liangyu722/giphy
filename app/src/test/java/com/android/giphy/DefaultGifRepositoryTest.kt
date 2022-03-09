package com.android.giphy

import app.cash.turbine.test
import com.android.giphy.common.Result
import com.android.giphy.data.DefaultGifRepository
import com.android.giphy.data.cache.GifCache
import com.android.giphy.data.networking.GiphyService
import com.android.giphy.data.networking.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.mock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DefaultGifRepositoryTest {

    private val dispatcher = Dispatchers.Unconfined
    private lateinit var sut: DefaultGifRepository

    private val service: GiphyService = mock()
    private val cache: GifCache = mock()

    @Before
    fun setup() {
        `when`(cache.gifFlow).thenReturn(flow { })
        sut = DefaultGifRepository(service, cache, dispatcher)
    }

    @Test
    fun `getGifs - server throws exception, return Error`() = runBlockingTest {
        `when`(cache.isCacheEmpty()).thenReturn(true)
        `when`(service.getTrendingGifs()).thenThrow(RuntimeException("some exception"))
        val result = sut.getGifs()
        result `should be instance of` (Result.Error::class)
    }

    @Test
    fun `getGifs - cache empty call server to retrieve gifs`() = runBlockingTest {
        `when`(cache.isCacheEmpty()).thenReturn(true)
        `when`(service.getTrendingGifs()).thenReturn(gifResponse)

        val result = sut.getGifs()
        verify(service, times(1)).getTrendingGifs()
        verify(cache, times(1)).addToCache(anyList())

        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `getGifs - cache has data avoid calling server`() = runBlockingTest {
        `when`(cache.isCacheEmpty()).thenReturn(false)

        val result = sut.getGifs()
        verify(service, times(0)).getTrendingGifs()
        verify(cache, times(0)).addToCache(anyList())

        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `gifFlow - verify flow`() = runBlockingTest {
        //Arrange
        `when`(cache.gifFlow).thenReturn(flow {
            emit(
                listOf(gif)
            )
        })
        sut = DefaultGifRepository(service, cache, dispatcher)
        //Act
        sut.gifFlow.test {
            //Assert
            awaitItem() shouldEqual listOf(gif)
            awaitComplete()
        }
    }

    //------------------------------------------------------------------------------------------------
    private val gif = com.android.giphy.model.Gif(
        "type",
        "id",
        "Original URL",
        "preview URL",
        "title"
    )

    private val original = Gif("10", "Original URL")
    private val preview = Gif("1", "preview URL")
    private val image = Images(original, preview)
    private val data = Data("type", "id", image, "title")

    private val pagination = Pagination(1, 1, 1)
    private val gifResponse = GifResponse(listOf(data), pagination)

}
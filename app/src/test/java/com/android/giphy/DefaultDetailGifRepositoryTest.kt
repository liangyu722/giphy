package com.android.giphy

import com.android.giphy.common.Result
import com.android.giphy.data.DefaultDetailGifRepository
import com.android.giphy.data.cache.GifCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DefaultDetailGifRepositoryTest {

    private val dispatcher = Dispatchers.Unconfined
    private lateinit var sut: DefaultDetailGifRepository

    private val cache: GifCache = mock()

    @Before
    fun setup() {
        sut = DefaultDetailGifRepository(cache, dispatcher)
    }

    @Test
    fun `viewGif - cache empty return error`() = runBlockingTest {
        `when`(cache.isCacheEmpty()).thenReturn(true)
        `when`(cache.getGifById(anyString())).thenReturn(null)
        val result = sut.viewGif("1")
        result `should be instance of` (Result.Error::class)
    }

    @Test
    fun `viewGif - cache return gif, return success`() = runBlockingTest {
        `when`(cache.isCacheEmpty()).thenReturn(false)
        `when`(cache.getGifById(anyString())).thenReturn(gif)
        val result = sut.viewGif("1")
        verify(cache, times(1)).getGifById("1")
        result `should be instance of` (Result.Success::class)
        (result as Result.Success).data `should equal` gif
    }

    //------------------------------------------------------------------------------------------------
    private val gif = com.android.giphy.model.Gif(
        "type",
        "id",
        "Original URL",
        "preview URL",
        "title"
    )
}
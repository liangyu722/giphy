package com.android.giphy.data.networking

import com.android.giphy.data.networking.model.GifResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("gifs/trending")
    suspend fun getTrendingGifs(@Query("offset") offset: Int): GifResponse

}
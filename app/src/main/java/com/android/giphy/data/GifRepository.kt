package com.android.giphy.data

import com.android.giphy.common.Result
import com.android.giphy.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    val gifFlow: Flow<List<Gif>>

    suspend fun getGifs() : Result<Unit>
}
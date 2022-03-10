package com.android.giphy.data

import androidx.paging.PagingData
import com.android.giphy.common.Result
import com.android.giphy.data.networking.model.Data
import com.android.giphy.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    val gifFlow: Flow<List<Gif>>

    val pagingDataFlow : Flow<PagingData<Gif>>

    suspend fun getGifs() : Result<Unit>
}
package com.android.giphy.data

import com.android.giphy.common.Result
import com.android.giphy.model.Gif

interface DetailGifRepository {

    suspend fun viewGif(id: String): Result<Gif>
}
package com.android.giphy.data.networking.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    val offset: Int,
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int
)
package com.android.giphy.data.networking.model

data class GifResponse(
    val data: List<Data>,
    val pagination: Pagination
)
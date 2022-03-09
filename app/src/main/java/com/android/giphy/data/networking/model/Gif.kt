package com.android.giphy.data.networking.model


import com.google.gson.annotations.SerializedName

data class Gif(
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String,
)
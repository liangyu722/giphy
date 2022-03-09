package com.android.giphy.data.networking.model


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("original")
    val original: Gif,
    @SerializedName("preview_gif")
    val previewGif: Gif,
)
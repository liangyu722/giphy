package com.android.giphy.data.common

import com.android.giphy.data.networking.model.Data
import com.android.giphy.data.networking.model.GifResponse
import com.android.giphy.model.Gif

fun GifResponse.toGifs(): List<Gif> {
    return this.data.map {
        Gif(it.type, it.id, it.images.original.url, it.images.previewGif.url, it.title)
    }
}

fun Data.toGifs(): Gif {
    return Gif(type, id, images.original.url, images.previewGif.url, title)
}
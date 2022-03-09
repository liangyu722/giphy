package com.android.giphy.data.networking.model

data class Data (
    val type: String,
    val id: String,
    val images: Images,
    val title: String)
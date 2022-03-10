package com.android.giphy.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("app:items")
fun <T> setItems(listView: RecyclerView, items: List<T>?) {
    if (listView.adapter is ListAdapter<*, *>) {
        (listView.adapter as ListAdapter<T, *>).submitList(items)
    }
}

@BindingAdapter("app:image")
fun <T> setImage(imageview: ImageView, url: String?) {
    url?.let {
        Glide
            .with(imageview.context)
            .load(url)
            .into(imageview)
    }
}

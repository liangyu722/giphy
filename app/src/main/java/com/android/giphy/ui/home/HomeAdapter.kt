package com.android.giphy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.giphy.databinding.GifItemBinding
import com.android.giphy.model.Gif
import java.text.DateFormat

class HomeAdapter(
    private val viewModel: HomeViewModel
) : ListAdapter<Gif, HomeAdapter.ViewHolder>(GifDiffUtilCallback()) {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(viewModel, item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder.from(parent)
        }

        class ViewHolder private constructor(private val binding: GifItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(
                viewModel: HomeViewModel,
                item: Gif
            ) {
                binding.viewmodel = viewModel
                binding.gif = item
                binding.executePendingBindings()
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = GifItemBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
                }
            }
        }
}

class GifDiffUtilCallback : DiffUtil.ItemCallback<Gif>() {
    override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem == newItem
    }
}
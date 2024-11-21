package com.bignerdranch.android.cs4750_movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.GalleryItem
import com.bignerdranch.android.cs4750_movieapp.databinding.ListItemGalleryBinding

class MovieViewHolder(
    private val binding: ListItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryItem: GalleryItem) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${galleryItem.poster_path}"
    }
}

class MovieListAdapter(
    private val galleryItems: List<GalleryItem>
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = galleryItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = galleryItems.size
}
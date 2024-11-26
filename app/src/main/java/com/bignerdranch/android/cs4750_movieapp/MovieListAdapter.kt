package com.bignerdranch.android.cs4750_movieapp

import android.util.Log
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.GalleryItem
import com.bignerdranch.android.cs4750_movieapp.databinding.ListItemGalleryBinding
import com.bignerdranch.android.cs4750_movieapp.databinding.ListItemMovieBinding

class MovieViewHolder(
    private val binding: ListItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(galleryItem: GalleryItem) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${galleryItem.poster_path}"
        Glide.with(binding.root.context)
            .load(imageUrl)
            .into(binding.moviePoster) // Replace with actual ImageView ID
        Log.d("MovieViewHolder", "Setting title: ${galleryItem.title}")
        binding.movieTitle.text = galleryItem.title  // Set the movie title
    }
}

class MovieListAdapter(
    private val galleryItems: List<GalleryItem>
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = galleryItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = galleryItems.size
}

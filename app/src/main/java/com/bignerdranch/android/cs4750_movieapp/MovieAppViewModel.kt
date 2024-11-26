package com.bignerdranch.android.cs4750_movieapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MovieAppViewModel"

class MovieAppViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    init {
       fetchPopularMovies()
    }
    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val items = movieRepository.fetchMovies()
                Log.d(TAG, "Items received: $items")
                _galleryItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val searchResults = movieRepository.searchMovies(query)
                Log.d(TAG, "Search results: $searchResults")
                _galleryItems.value = searchResults
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to search for movies", ex)
            }
        }
    }
}
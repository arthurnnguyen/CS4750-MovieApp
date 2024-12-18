package com.bignerdranch.android.cs4750_movieapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.GalleryItem
import api.MovieGenre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MovieAppViewModel"

class MovieAppViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    private val _genres = MutableStateFlow<List<MovieGenre>>(emptyList())
    val genres: StateFlow<List<MovieGenre>> get() = _genres

    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    init {
       fetchPopularMovies()
        fetchGenres()
    }
    fun fetchPopularMovies() {
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

    private fun fetchGenres() {
        viewModelScope.launch {
            try {
                val genresList = movieRepository.fetchGenres()
                _genres.value = genresList
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch genres", ex)
            }
        }
    }
    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            try {
                val genreMovies = movieRepository.getMoviesByGenre(genreId)
                Log.d(TAG, "Genre movies: $genreMovies")
                _galleryItems.value = genreMovies
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch movies by genre", ex)
            }
        }
    }

}
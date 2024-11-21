package com.bignerdranch.android.cs4750_movieapp

import android.util.Log
import api.GalleryItem
import api.TMDBApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MovieRepository {
    private val tmdbApi: TMDBApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        tmdbApi = retrofit.create(TMDBApi::class.java)
    }

    suspend fun fetchMovies(): List<GalleryItem> {
        return try {
            val response = tmdbApi.getPopularMovies(
                apiKey = "e9a1665722447d1cfcba11b0f2032dee",
                language = "en-US",
                page = 1
            )


            response.results.map { movie ->
                GalleryItem(
                    id = movie.id,
                    title = movie.title,
                    poster_path = movie.poster_path ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movies", e)
            emptyList()
        }
    }
}


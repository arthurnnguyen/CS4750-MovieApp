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

            Log.d("MovieRepository", "Popular API response: ${response.results}")


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


    suspend fun searchMovies(query: String): List<GalleryItem> {
        return try {
            val response = tmdbApi.searchMovies(
                apiKey = "e9a1665722447d1cfcba11b0f2032dee",
                query = query,
                language = "en-US",
                page = 1,
                includeAdult = false
            )

            Log.d("MovieRepository", "Search API response: ${response.results}")


            response.results.map { movie ->
                GalleryItem(
                    id = movie.id,
                    title = movie.title,
                    poster_path = movie.poster_path ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error searching movies", e)
            emptyList()
        }
    }
}


package com.bignerdranch.android.cs4750_movieapp

import android.util.Log
import api.GalleryItem
import api.MovieDetail
import api.MovieGenre
import api.TMDBApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MovieRepository {
    private val tmdbApi: TMDBApi
    private val API_KEY = "e9a1665722447d1cfcba11b0f2032dee"
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
                GalleryItem(id = movie.id,
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

    suspend fun fetchMovieDetails(movieId: Int): MovieDetail? {
        return try {
            tmdbApi.getMovieDetails(
                movieId = movieId,
                apiKey = "e9a1665722447d1cfcba11b0f2032dee"
            )
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movie details", e)
            null
        }
    }

    suspend fun fetchRecommendedMovies(movieId: Int): List<GalleryItem> {
        return try {
            val response = tmdbApi.getRecommendedMovies(
                movieId = movieId,
                apiKey = API_KEY,
                language = "en-US"
            )
            if (response.results.isNotEmpty()) {
                response.results // This should directly return the list of GalleryItems
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching recommended movies", e)
            emptyList()
        }
    }

    suspend fun fetchGenres(): List<MovieGenre> {
        return try {
            val response = tmdbApi.getGenres(
                apiKey = API_KEY,
                language = "en-US"
            )
            response.genres // Return the genres from the response
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching genres", e)
            emptyList()
        }
    }

    // Fetch movies filtered by genre
    suspend fun getMoviesByGenre(genreId: Int): List<GalleryItem> {
        return try {
            val response = tmdbApi.getMoviesByGenre(
                apiKey = API_KEY,
                genreId = genreId,
                language = "en-US",
                page = 1
            )

            response.results.map { movie ->
                GalleryItem(id = movie.id, title = movie.title, poster_path = movie.poster_path ?: "")
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movies by genre", e)
            emptyList()
        }
    }

}


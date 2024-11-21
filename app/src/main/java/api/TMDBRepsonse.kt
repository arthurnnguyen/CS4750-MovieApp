package api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBResponse(
    val movies: MovieResponse
)
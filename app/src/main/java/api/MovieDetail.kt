package api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String?
)
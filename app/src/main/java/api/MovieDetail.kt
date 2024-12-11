package api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val genres: List<Genre>,
    val budget: Int,
    val imdb_id: String?,
    val production_companies: List<ProductionCompany>,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String
)

@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    val name: String,
    val logo_path: String?,
    val origin_country: String
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)
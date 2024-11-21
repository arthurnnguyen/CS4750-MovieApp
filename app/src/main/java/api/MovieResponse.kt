package api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val page: Int,
    val results: List<GalleryItem>,
    val total_pages: Int,
    val total_results: Int
)
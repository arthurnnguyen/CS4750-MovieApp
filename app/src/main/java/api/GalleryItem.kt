package api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val id: Int,
    val title: String,
    val poster_path: String?
    )

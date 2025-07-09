package me.teyatha.albums.domain

internal data class Album(
    val name: String,
    val images: List<Image>,
    val priceFormatted: String,
    val link: String,
    val artist: String,
    val releaseDateFormatted: String,
) {
    data class Image(
        val url: String,
        val heightPx: Int,
    )
}
package me.teyatha.albums.domain

internal data class FeedList(
    val author: Author,
    val albums: List<Album>,
) {
    data class Author(
        val name: String,
        val url: String,
    )
}
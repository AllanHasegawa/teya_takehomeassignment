package me.teyatha.albums.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
internal data class FeedResponse(
    val feed: FeedContentResponse,
) {
    @Serializable
    @JsonIgnoreUnknownKeys
    internal data class FeedContentResponse(
        val author: AuthorResponse,
        val entry: List<EntryResponse>,
    )

    @Serializable
    internal data class AuthorResponse(
        val name: PropertyResponse,
        val uri: PropertyResponse,
    )

    @Serializable
    internal data class PropertyResponse(
        val label: String? = null,
        val attributes: Map<String, String>? = null,
    )

    @Serializable
    @JsonIgnoreUnknownKeys
    internal data class EntryResponse(
        @SerialName("im:name") val name: PropertyResponse,
        @SerialName("im:image") val image: List<PropertyResponse>,
        @SerialName("im:price") val price: PropertyResponse,
        @SerialName("link") val link: PropertyResponse,
        @SerialName("im:artist") val artist: PropertyResponse,
        @SerialName("im:releaseDate") val releaseDate: PropertyResponse,
    )
}
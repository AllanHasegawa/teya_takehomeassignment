package me.teyatha.albums.data.responses

import me.teyatha.albums.domain.Album
import me.teyatha.albums.domain.FeedList
import javax.inject.Inject

internal class FeedResponseMapper @Inject constructor() {
    fun map(response: FeedResponse) =
        FeedList(
            author = response.feed.author.toDomain(),
            albums = response.feed.entry.map {
                it.toAlbumDomain()
            }
        )

    private fun FeedResponse.AuthorResponse.toDomain(): FeedList.Author =
        FeedList.Author(
            name = name.label!!,
            url = uri.label!!,
        )

    private fun FeedResponse.EntryResponse.toAlbumDomain(): Album =
        Album(
            id = id.label!!,
            name = name.label!!,
            images = image.map {
                Album.Image(
                    url = it.label!!,
                    heightPx = it.attributes!!["height"]!!.toInt()
                )
            },
            priceFormatted = price.label!!,
            link = link.attributes!!["href"]!!,
            artist = artist.label!!,
            releaseDateFormatted = releaseDate.attributes!!["label"]!!,
        )
}


package me.teyatha.albums.data.responses

import kotlinx.serialization.json.Json
import me.teyatha.albums.data.responses.FeedResponse.PropertyResponse
import org.junit.Test
import kotlin.test.assertEquals

class FeedResponseSerializerTest {
    private val rawJson = """
{"feed":{"author":{"name":{"label":"iTunes Store"}, "uri":{"label":"http://www.apple.com/itunes/"}}, "entry":[
    {"im:name":{"label":"Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s)"}, "im:image":[
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/55x55bb.png", "attributes":{"height":"55"}},
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/60x60bb.png", "attributes":{"height":"60"}},
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/170x170bb.png", "attributes":{"height":"170"}}], "im:itemCount":{"label":"13"}, "im:price":{"label":"$9.99", "attributes":{"amount":"9.99", "currency":"USD"}}, "im:contentType":{"im:contentType":{"attributes":{"term":"Album", "label":"Album"}}, "attributes":{"term":"Music", "label":"Music"}}, "rights":{"label":"This Compilation ℗ 1985 Geffen Records"}, "title":{"label":"Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s) - Jimmy Buffett"}, "link":{"attributes":{"rel":"alternate", "type":"text/html", "href":"https://music.apple.com/us/album/songs-you-know-by-heart-jimmy-buffetts-greatest-hit-s/1440673653?uo=2"}}, "id":{"label":"https://music.apple.com/us/album/songs-you-know-by-heart-jimmy-buffetts-greatest-hit-s/1440673653?uo=2", "attributes":{"im:id":"1440673653"}}, "im:artist":{"label":"Jimmy Buffett", "attributes":{"href":"https://music.apple.com/us/artist/jimmy-buffett/61232?uo=2"}}, "category":{"attributes":{"im:id":"10", "term":"Singer/Songwriter", "scheme":"https://music.apple.com/us/genre/music-singer-songwriter/id10?uo=2", "label":"Singer/Songwriter"}}, "im:releaseDate":{"label":"1985-10-14T00:00:00-07:00", "attributes":{"label":"October 14, 1985"}}}], "updated":{"label":"2025-07-08T12:52:03-07:00"}, "rights":{"label":"Copyright 2008 Apple Inc."}, "title":{"label":"iTunes Store: Top Albums"}, "icon":{"label":"http://itunes.apple.com/favicon.ico"}, "link":[
    {"attributes":{"rel":"alternate", "type":"text/html", "href":"https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?cc=us&id=1&popId=11"}},
    {"attributes":{"rel":"self", "href":"https://itunes.apple.com/us/rss/topalbums/limit=100/json"}}], "id":{"label":"https://itunes.apple.com/us/rss/topalbums/limit=100/json"}}}
"""

    @Test
    fun `FeedResponse serializes properly`() {
        val expected = FeedResponse(
            feed = FeedResponse.FeedContentResponse(
                author = FeedResponse.AuthorResponse(
                    name = PropertyResponse(
                        label = "iTunes Store",
                    ),
                    uri = PropertyResponse(
                        label = "http://www.apple.com/itunes/"
                    )
                ),
                entry = listOf(
                    FeedResponse.EntryResponse(
                        name = PropertyResponse(
                            label = "Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s)",
                        ),
                        image = listOf(
                            PropertyResponse(
                                label = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/55x55bb.png",
                                attributes = mapOf("height" to "55"),
                            ),
                            PropertyResponse(
                                label = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/60x60bb.png",
                                attributes = mapOf("height" to "60"),
                            ),
                            PropertyResponse(
                                label = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/170x170bb.png",
                                attributes = mapOf("height" to "170"),
                            ),
                        ),
                        price = PropertyResponse(
                            label = "$9.99",
                            attributes = mapOf(
                                "amount" to "9.99",
                                "currency" to "USD"
                            ),
                        ),
                        link = PropertyResponse(
                            attributes = mapOf(
                                "rel" to "alternate",
                                "type" to "text/html",
                                "href" to "https://music.apple.com/us/album/songs-you-know-by-heart-jimmy-buffetts-greatest-hit-s/1440673653?uo=2",
                            )
                        ),
                        artist = PropertyResponse(
                            label = "Jimmy Buffett",
                            attributes = mapOf(
                                "href" to "https://music.apple.com/us/artist/jimmy-buffett/61232?uo=2"
                            ),
                        ),
                        releaseDate = PropertyResponse(
                            label = "1985-10-14T00:00:00-07:00",
                            attributes = mapOf(
                                "label" to "October 14, 1985"
                            )
                        ),
                    ),
                )
            )
        )

        val parsed = Json.decodeFromString<FeedResponse>(rawJson)
        assertEquals(expected, parsed)
    }
}
package me.teyatha

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.teyatha.core.CoreModule
import me.teyatha.core.Delayer
import me.teyatha.core.DispatcherIo
import me.teyatha.core.Randomiser
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreModule::class],
)
object TestModule {
    @Provides
    fun httpEngine(): HttpClientEngine = MockEngine { request ->
        val rawJson = """
{"feed":{"author":{"name":{"label":"iTunes Store"}, "uri":{"label":"http://www.apple.com/itunes/"}}, "entry":[
    {"im:name":{"label":"Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s)"}, "im:image":[
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/55x55bb.png", "attributes":{"height":"55"}},
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/60x60bb.png", "attributes":{"height":"60"}},
        {"label":"https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/9b/fc/52/9bfc52bf-6593-cdd7-013f-7b7a1fe21efe/06UMGIM04213.rgb.jpg/170x170bb.png", "attributes":{"height":"170"}}], "im:itemCount":{"label":"13"}, "im:price":{"label":"$9.99", "attributes":{"amount":"9.99", "currency":"USD"}}, "im:contentType":{"im:contentType":{"attributes":{"term":"Album", "label":"Album"}}, "attributes":{"term":"Music", "label":"Music"}}, "rights":{"label":"This Compilation ℗ 1985 Geffen Records"}, "title":{"label":"Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s) - Jimmy Buffett"}, "link":{"attributes":{"rel":"alternate", "type":"text/html", "href":"https://music.apple.com/us/album/songs-you-know-by-heart-jimmy-buffetts-greatest-hit-s/1440673653?uo=2"}}, "id":{"label":"https://music.apple.com/us/album/songs-you-know-by-heart-jimmy-buffetts-greatest-hit-s/1440673653?uo=2", "attributes":{"im:id":"1440673653"}}, "im:artist":{"label":"Jimmy Buffett", "attributes":{"href":"https://music.apple.com/us/artist/jimmy-buffett/61232?uo=2"}}, "category":{"attributes":{"im:id":"10", "term":"Singer/Songwriter", "scheme":"https://music.apple.com/us/genre/music-singer-songwriter/id10?uo=2", "label":"Singer/Songwriter"}}, "im:releaseDate":{"label":"1985-10-14T00:00:00-07:00", "attributes":{"label":"October 14, 1985"}}}], "updated":{"label":"2025-07-08T12:52:03-07:00"}, "rights":{"label":"Copyright 2008 Apple Inc."}, "title":{"label":"iTunes Store: Top Albums"}, "icon":{"label":"http://itunes.apple.com/favicon.ico"}, "link":[
    {"attributes":{"rel":"alternate", "type":"text/html", "href":"https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?cc=us&id=1&popId=11"}},
    {"attributes":{"rel":"self", "href":"https://itunes.apple.com/us/rss/topalbums/limit=100/json"}}], "id":{"label":"https://itunes.apple.com/us/rss/topalbums/limit=100/json"}}}
"""
        respond(
            content = ByteReadChannel(rawJson),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    @Provides
    @Singleton
    fun randomiser(): Randomiser = object : Randomiser {
        override fun weightedBoolean(chanceForTrue: Float): Boolean = true
    }

    @Provides
    @Singleton
    fun delayer(): Delayer = object : Delayer {
        override suspend fun delayForABit() = Unit
    }

    @Provides
    @Singleton
    @DispatcherIo
    fun dispatcherIo(): CoroutineDispatcher = Dispatchers.IO
}
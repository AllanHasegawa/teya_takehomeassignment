package me.teyatha.albums.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    fun httpClient() = HttpClient(OkHttp) {
        followRedirects = true
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }
}
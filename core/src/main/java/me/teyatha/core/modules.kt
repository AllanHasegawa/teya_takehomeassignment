package me.teyatha.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIo

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun httpEngine(): HttpClientEngine = OkHttp.create()

    @Provides
    @Singleton
    fun randomiser(): Randomiser = KotlinRandomiser()

    @Provides
    @Singleton
    fun delayer(): Delayer = KotlinDelayer

    @Provides
    @Singleton
    @DispatcherIo
    fun dispatcherIo(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    fun httpClient(engine: HttpClientEngine): HttpClient =
        HttpClient(engine) {
            followRedirects = true
            install(ContentNegotiation) {
                val json = Json {
                    prettyPrint = true
                    isLenient = true
                }
                register(
                    contentType = ContentType.Text.JavaScript,
                    converter = KotlinxSerializationConverter(json)
                )
                json(json)
            }
        }
}
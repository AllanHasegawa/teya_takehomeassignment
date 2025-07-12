package me.teyatha.albums.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.teyatha.albums.data.responses.FeedResponse
import me.teyatha.albums.data.responses.FeedResponseMapper
import me.teyatha.albums.domain.FeedList
import javax.inject.Inject

internal class AlbumsRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val feedResponseMapper: FeedResponseMapper,
) {
    private companion object {
        private const val URL = "https://itunes.apple.com/us/rss/topalbums/limit=100/json"
    }

    /**
     * Returning as [Flow] even when a simple suspend function would suffice
     * as it would make it easy to handle caching in the future,
     * or any side-effect that would alter the data.
     */
    fun getFeedList(): Flow<Result<FeedList>> =
        flow {
            try {
                val response: FeedResponse = httpClient.get(URL).body()
                val mapped = feedResponseMapper.map(response)
                emit(Result.success(mapped))
            } catch (t: Throwable) {
                emit(Result.failure(t))
                null
            }
        }
}
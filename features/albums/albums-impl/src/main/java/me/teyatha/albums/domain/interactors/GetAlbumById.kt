package me.teyatha.albums.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import me.teyatha.albums.data.AlbumsRepository
import me.teyatha.albums.domain.Album
import javax.inject.Inject

internal class GetAlbumById @Inject constructor(
    private val repository: AlbumsRepository,
) {
    operator fun invoke(albumId: String): Flow<Result<Album?>> =
        repository.getFeedList().mapLatest { result ->
            result.map { list ->
                list.albums.firstOrNull { album ->
                    album.id == albumId
                }
            }
        }
}
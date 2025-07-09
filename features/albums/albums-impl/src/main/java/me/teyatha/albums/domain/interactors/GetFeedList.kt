package me.teyatha.albums.domain.interactors

import me.teyatha.albums.data.AlbumsRepository
import javax.inject.Inject

internal class GetFeedList @Inject constructor(
    private val repository: AlbumsRepository,
) {
    operator fun invoke() = repository.getFeedList()
}
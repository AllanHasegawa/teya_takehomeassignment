package me.teyatha.albums.navigation

import androidx.navigation3.runtime.NavKey

data object AlbumsListScreen : NavKey
data class AlbumDetailsScreen(val albumId: String) : NavKey
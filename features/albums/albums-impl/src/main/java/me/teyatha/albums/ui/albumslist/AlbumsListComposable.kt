package me.teyatha.albums.ui.albumslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlbumsListComposable(
    onAlbumClick: (albumId: String) -> Unit,
) {
    Scaffold { contentPadding ->
        Text(
            modifier = Modifier
                .padding(contentPadding)
                .clickable(true, onClick = {
                    onAlbumClick("123")
                }), text = "List"
        )
    }
}
package me.teyatha.albums.ui.albumdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.teyatha.albums.navigation.AlbumDetailsScreen

@Composable
fun AlbumDetailsComposable(
    key: AlbumDetailsScreen,
    onBack: () -> Unit,
) {
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Text(text = "Details for ${key.albumId}")
            Button({
                onBack()
            }) { Text(text = "Go back") }
        }
    }
}
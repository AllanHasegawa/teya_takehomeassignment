package me.teyatha.albums.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import me.teyatha.albums.navigation.AlbumDetailsScreen
import me.teyatha.albums.navigation.AlbumsListScreen
import me.teyatha.core.EntryProviderInstaller
import me.teyatha.core.Navigator

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object UiModule {
    @IntoSet
    @Provides
    fun entryProviderInstaller(navigator: Navigator): EntryProviderInstaller = {
        entry<AlbumsListScreen> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(modifier = Modifier.clickable(true, onClick = {
                    navigator.goTo(AlbumDetailsScreen("123"))
                }), text = "List")
            }
        }

        entry<AlbumDetailsScreen> { key ->
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Details for ${key.albumId}")
                Button({
                    navigator.goBack()
                }) { Text(text = "Go back") }
            }
        }
    }
}
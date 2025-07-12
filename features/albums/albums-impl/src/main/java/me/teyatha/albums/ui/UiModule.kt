package me.teyatha.albums.ui

import androidx.navigation3.runtime.entry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import me.teyatha.albums.navigation.AlbumDetailsScreen
import me.teyatha.albums.navigation.AlbumsListScreen
import me.teyatha.albums.ui.albumdetails.AlbumDetailPage
import me.teyatha.albums.ui.albumslist.AlbumsListPage
import me.teyatha.core.EntryProviderInstaller
import me.teyatha.core.Navigator

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object UiModule {
    @IntoSet
    @Provides
    fun entryProviderInstaller(navigator: Navigator): EntryProviderInstaller = {
        entry<AlbumsListScreen> {
            AlbumsListPage(
                onAlbumClick = { navigator.goTo(AlbumDetailsScreen(it)) })
        }

        entry<AlbumDetailsScreen> { key ->
            AlbumDetailPage(
                key = key, onBack = { navigator.goBack() })
        }
    }
}
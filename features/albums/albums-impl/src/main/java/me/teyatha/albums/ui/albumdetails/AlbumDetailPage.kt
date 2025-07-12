package me.teyatha.albums.ui.albumdetails

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import dagger.hilt.android.lifecycle.withCreationCallback
import me.teyatha.albums.R
import me.teyatha.albums.navigation.AlbumDetailsScreen
import me.teyatha.albums.ui.albumdetails.AlbumDetailsViewModel.ViewState
import me.teyatha.ds.Dimensions
import me.teyatha.ds.theme.Typography
import me.teyatha.ds.theme.components.NavigationItem
import me.teyatha.ds.theme.components.PropertyItem

@Composable
fun AlbumDetailPage(
    key: AlbumDetailsScreen,
    onBack: () -> Unit,
) {
    val viewModelStoreOwner = requireNotNull(
        LocalViewModelStoreOwner.current as? HasDefaultViewModelProviderFactory
    )
    val viewModel: AlbumDetailsViewModel = viewModel(
        extras =
            viewModelStoreOwner
                .defaultViewModelCreationExtras
                .withCreationCallback<AlbumDetailsViewModel.Factory> { factory ->
                    factory.create(albumId = key.albumId)
                }
    )
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val uriHandler = LocalUriHandler.current

    val album = (state as? ViewState.Content)?.album
        ?: return

    Scaffold { contentPadding ->
        LazyColumn(Modifier.consumeWindowInsets(contentPadding)) {
            item {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = album.images.maxBy { it.heightPx }.url,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(top = 16.dp),
                    text = album.name,
                    style = Typography.displayLarge,
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(top = 8.dp, bottom = 32.dp),
                    text = album.artist,
                    style = Typography.bodyLarge,
                )
            }
            item {
                PropertyItem(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(vertical = 8.dp),
                    title = stringResource(R.string.property_price_title),
                    label = album.priceFormatted
                )
            }
            item {
                PropertyItem(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(vertical = 8.dp),
                    title = stringResource(R.string.property_release_title),
                    label = album.releaseDateFormatted
                )
            }
            item {
                NavigationItem(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(vertical = 8.dp),
                    text = stringResource(R.string.property_link_title),
                    onClick = { uriHandler.openUri(album.link) },
                    icon = me.teyatha.ds.R.drawable.ic_chevron_right,
                )
            }
        }
    }
}
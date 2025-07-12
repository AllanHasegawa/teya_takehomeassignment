package me.teyatha.albums.ui.albumslist

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.teyatha.albums.ui.albumslist.AlbumsListViewModel.ViewState
import me.teyatha.ds.Dimensions
import me.teyatha.ds.theme.Typography

@Composable
fun AlbumsListPage(
    onAlbumClick: (albumId: String) -> Unit,
) {
    val viewModel: AlbumsListViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle(
        ViewState.Loading,
    )

    val feed = (state.value as? ViewState.Content)?.feed

    Scaffold { contentPadding ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(contentPadding)
        ) {
            item("header") {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(
                            top = 64.dp,
                            bottom = 32.dp,
                        ),
                    text = feed?.author?.name ?: "",
                    style = Typography.displayLarge,
                )
            }
            feed?.albums?.forEach {
                item(it.id) {
                    AlbumCover(it, onClick = { onAlbumClick(it.id) })
                }
            }
        }
    }
}
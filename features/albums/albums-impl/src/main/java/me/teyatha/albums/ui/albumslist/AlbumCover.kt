package me.teyatha.albums.ui.albumslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import me.teyatha.albums.R
import me.teyatha.albums.domain.Album
import me.teyatha.ds.Dimensions
import me.teyatha.ds.theme.Typography

@Composable
internal fun AlbumCover(
    album: Album,
    onClick: () -> Unit,
) {
    val image = remember {
        album.images
            .filter { it.heightPx < 300 }
            .maxByOrNull { it.heightPx }
            ?: album.images.firstOrNull()
            ?: Album.Image(
                url = "placeholder",
                heightPx = 170,
            )
    }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(true, onClick = onClick),
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = image.url,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .background(Color.Black.copy(alpha = 0.4f))
        ) {
            Text(
                modifier =
                    Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(top = 16.dp),
                text = album.name,
                style = Typography.bodyLarge,
            )
            Text(
                modifier =
                    Modifier
                        .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
                        .padding(top = 4.dp, bottom = 16.dp),
                text = album.artist,
                style = Typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
internal fun AlbumCoverPreview() {
    val context = LocalContext.current
    val painter = painterResource(R.drawable.album_cover_preview)
    val previewHandler = AsyncImagePreviewHandler { loader, request ->
        val image = loader.execute(
            ImageRequest.Builder(context)
                .data(R.drawable.album_cover_preview)
                .build()
        ).image!!
        AsyncImagePainter.State.Success(
            painter,
            SuccessResult(image, request)
        )
    }

    Box(modifier = Modifier.background(Color.White)) {
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
            AlbumCover(
                album = Album(
                    id = "id",
                    name = "Album name",
                    images = listOf(Album.Image("", 170)),
                    priceFormatted = "",
                    link = "",
                    artist = "Album Artist Jr.",
                    releaseDateFormatted = ""
                ),
                onClick = {},
            )
        }
    }
}
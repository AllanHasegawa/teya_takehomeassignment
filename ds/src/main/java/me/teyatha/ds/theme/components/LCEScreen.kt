package me.teyatha.ds.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import me.teyatha.core.LCE
import me.teyatha.ds.Dimensions
import me.teyatha.ds.R
import me.teyatha.ds.theme.Typography

@Composable
fun <T> LCEScreen(
    lce: LCE<T>,
    onErrorRetry: () -> Unit,
    content: @Composable (T) -> Unit,
) {
    when (lce) {
        is LCE.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("Loader"),
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }
        }

        is LCE.Error -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .testTag("Error")
                    .padding(horizontal = Dimensions.SCREEN_HORIZONTAL_PADDING)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.error_title),
                        style = Typography.displayLarge,
                    )
                    NavigationItem(
                        text = stringResource(R.string.error_retry_cta),
                        onClick = onErrorRetry,
                    )
                }
            }
        }

        is LCE.Content -> content(lce.value)
    }
}
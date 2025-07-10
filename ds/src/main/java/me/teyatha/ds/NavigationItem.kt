package me.teyatha.ds

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.teyatha.ds.Dimensions.SCREEN_HORIZONTAL_PADDING

@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    @DrawableRes icon: Int? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SCREEN_HORIZONTAL_PADDING)
            .padding(vertical = 8.dp)
            .border(0.5.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .requiredHeight(48.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically),
            text = text,
            maxLines = 1,
            fontWeight = FontWeight.Normal,
        )

        if (icon != null) {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(12.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(icon),
                contentDescription = text,
            )
        }
    }
}

@Preview
@Composable
internal fun NavigationItemPreviewNoIcon() {
    Box(Modifier.background(Color.White)) {
        NavigationItem(
            text = "Navigate to there",
            onClick = {},
        )
    }
}

@Preview
@Composable
internal fun NavigationItemPreviewWithIcon() {
    Box(Modifier.background(Color.White)) {
        NavigationItem(
            text = "Navigate to there",
            onClick = {},
            icon = R.drawable.ic_chevron_right
        )
    }
}

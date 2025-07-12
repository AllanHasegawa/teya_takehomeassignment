package me.teyatha.ds.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.teyatha.ds.R
import me.teyatha.ds.theme.TeyaTakeHomeAssignmentTheme
import me.teyatha.ds.theme.Typography

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
            .padding(vertical = 8.dp)
            .border(0.5.dp, Color.White, shape = RoundedCornerShape(12.dp))
            .clickable(true, onClick = onClick)
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
            style = Typography.bodyMedium
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
    TeyaTakeHomeAssignmentTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            NavigationItem(
                text = "Navigate to there",
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
internal fun NavigationItemPreviewWithIcon() {
    TeyaTakeHomeAssignmentTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            NavigationItem(
                text = "Navigate to there",
                onClick = {},
                icon = R.drawable.ic_chevron_right
            )
        }
    }
}

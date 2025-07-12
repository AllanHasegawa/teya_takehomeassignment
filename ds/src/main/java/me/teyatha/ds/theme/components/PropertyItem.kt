package me.teyatha.ds.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.teyatha.ds.theme.Typography

@Composable
fun PropertyItem(
    modifier: Modifier,
    title: String,
    label: String,
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.width(80.dp),
            text = title,
            style = Typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = label,
            style = Typography.bodyMedium,
        )
    }
}
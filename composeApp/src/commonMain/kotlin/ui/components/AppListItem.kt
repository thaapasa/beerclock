package fi.tuska.beerclock.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppListItem(
    overline: String? = null,
    headline: String,
    supporting: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 8.dp
) {
    androidx.compose.material3.ListItem(
        overlineContent = overline?.let { { Text(it) } },
        headlineContent = { Text(headline) },
        supportingContent = supporting?.let { { Text(it) } },
        leadingContent = icon,
        trailingContent = trailingContent,
        modifier = modifier,
        tonalElevation = tonalElevation,
        shadowElevation = 16.dp
    )
}

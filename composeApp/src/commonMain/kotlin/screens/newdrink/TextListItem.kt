package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.ui.components.AppListItem

data class TextListInfo(
    val key: String,
    val name: String,
    val description: String? = null,
    val icon: AppIcon? = null,
    val onClick: () -> Unit = {},
)

@Composable
fun TextListItem(
    drink: TextListInfo,
    modifier: Modifier = Modifier,
) {
    AppListItem(
        headline = drink.name,
        supporting = drink.description,
        modifier = modifier.clickable(onClick = drink.onClick),
        trailingContent = {
            drink.icon?.icon(tint = MaterialTheme.colorScheme.primary)
        },
        tonalElevation = 1.dp,
    )
}

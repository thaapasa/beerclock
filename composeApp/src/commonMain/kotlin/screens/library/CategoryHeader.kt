package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.images.image
import fi.tuska.beerclock.ui.components.AppListItem

data class CategoryHeaderInfo(
    val category: Category?,
    val name: String,
    val onClick: () -> Unit = {},
) {
    val image = category?.image ?: DrinkImage.CAT_UNCATEGORIZED
    val key = category?.name ?: "no-category"
}

@Composable
fun CategoryHeaderItem(item: CategoryHeaderInfo, modifier: Modifier = Modifier) {
    AppListItem(
        headline = item.name,
        icon = {
            Column(
                modifier = Modifier.width(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item.image.image(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(50))
                )
            }
        },
        modifier = modifier,
        tonalElevation = 16.dp,
    )
}

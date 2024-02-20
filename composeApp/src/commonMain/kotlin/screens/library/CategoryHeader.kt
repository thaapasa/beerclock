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
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.images.image
import fi.tuska.beerclock.ui.components.AppListItem

class CategoryHeaderInfo(
    category: Category?,
    name: String,
    val onClick: () -> Unit = {},
) : BasicDrinkInfo(
    name = name,
    quantityCl = 0.0,
    abvPercentage = 0.0,
    image = category?.image ?: DrinkImage.GENERIC_DRINK,
) {
    override val key = category?.name ?: "no-category"
}

@Composable
fun CategoryHeaderItem(item: CategoryHeaderInfo, modifier: Modifier) {
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

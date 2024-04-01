package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.images.image


private val borderWidth = 3.dp
private val halfBorder = borderWidth / 2

@Composable
fun CategoryBar(
    modifier: Modifier = Modifier,
    selected: Category?,
    select: (category: Category?) -> Unit,
) {

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(50))
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Category.entries.map { cat ->
                CategoryEntry(
                    selected = selected == cat,
                    cat = cat,
                    select
                )
            }
            CategoryEntry(selected = selected == null, cat = null, select)
        }
    }
}

@Composable
fun RowScope.CategoryEntry(selected: Boolean, cat: Category?, select: (cat: Category?) -> Unit) {
    Box(
        modifier = Modifier.weight(1f).padding(1.dp)
            .let {
                if (selected) it.border(
                    halfBorder,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                else it
            }
            .padding(halfBorder)
            .clip(RoundedCornerShape(50))
    ) {
        (cat?.image ?: DrinkImage.CAT_UNCATEGORIZED).image(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (selected) it.border(
                        halfBorder,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    ) else it
                }
                .clip(RoundedCornerShape(50))
                .clickable { select(cat) }
        )
    }
}

package fi.tuska.beerclock.screens.drinks.editor

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.image
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DropdownSelect

val categoryOptions: Array<Category?> = arrayOf(*Category.entries.toTypedArray(), null)

@Composable
fun CategorySelector(
    selected: Category?,
    onSelect: (category: Category?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    DropdownSelect(
        options = categoryOptions,
        selected = selected,
        onSelect = onSelect,
        modifier = modifier,
        valueToText = strings.drink::categoryName,
        label = { Text(text = strings.drink.categoryLabel) },
        iconForValue = { cat ->
            cat?.image?.image(
                modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp))
            )
        }
    )
}

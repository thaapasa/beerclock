package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
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


@Composable
fun CategoryBar(
    modifier: Modifier = Modifier,
    selected: Set<Category>,
    toggle: (category: Category) -> Unit,
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
                cat.image.image(
                    modifier = Modifier
                        .let {
                            if (selected.contains(cat)) it.padding(2.dp)
                            else it.padding(3.dp)
                        }
                        .weight(1f)
                        .let {
                            if (selected.contains(cat)) it.border(
                                3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
                            else it
                        }
                        .clip(RoundedCornerShape(50))
                        .clickable { toggle(cat) }
                )
            }
        }
    }
}

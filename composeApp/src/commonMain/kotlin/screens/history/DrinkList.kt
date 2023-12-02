package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.DrinkRecordInfo

@Composable
fun DrinkList(
    drinkList: List<DrinkRecordInfo>,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    return LazyColumn(modifier = modifier) {
        items(drinkList) {
            DrinkListItem(it, onDelete = onDelete)
        }
    }
}
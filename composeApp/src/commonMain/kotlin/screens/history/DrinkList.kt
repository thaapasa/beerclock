package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.DrinkRecordInfo

@Composable
fun DrinkList(
    drinkList: List<DrinkRecordInfo>,
    onModify: ((drink: DrinkRecordInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    return LazyColumn(modifier = modifier) {
        items(drinkList) {
            DrinkListItem(it, onModify = onModify, onDelete = onDelete)
        }
    }
}

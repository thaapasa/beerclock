package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.database.DrinkRecord

@Composable
fun DrinksList(
    drinksList: List<DrinkRecord>,
    onClick: (drink: DrinkRecord) -> Unit = {},
    modifier: Modifier = Modifier
) {
    return LazyColumn(modifier = modifier) {
        items(drinksList) {
            DrinksListItem(it, onClick = { onClick(it) })
        }
    }
}
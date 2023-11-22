import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.components.DrinksListItem
import fi.tuska.beerclock.database.Drinks

@Composable
fun DrinksList(
    drinksList: List<Drinks>,
    onClick: (drink: Drinks) -> Unit = {},
    modifier: Modifier = Modifier
) {
    return LazyColumn(modifier = modifier) {
        items(drinksList) {
            DrinksListItem(it, onClick = { onClick(it) })
        }
    }
}
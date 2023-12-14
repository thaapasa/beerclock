package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.LatestDrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.ViewModel
import org.koin.core.component.KoinComponent

@Composable
fun LatestDrinks(onAddDrink: (drink: LatestDrinkInfo?) -> Unit) {
    val strings = Strings.get()
    val vm = remember { LatestDrinksViewModel() }
    LaunchedEffect(Unit) {
        vm.loadDrinks()
    }
    Column(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
        ListItem(
            headlineContent = { Text(strings.newdrink.latestDrinksTitle) },
            trailingContent = {
                IconButton(onClick = { onAddDrink(null) }) {
                    AppIcon.ADD_CIRCLE.icon(tint = MaterialTheme.colorScheme.primary)
                }
            },
            tonalElevation = 1.dp,
            shadowElevation = 16.dp
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(vm.latestDrinks) {
                LatestDrinkItem(it, onAddDrink)
            }
        }
    }
}

class LatestDrinksViewModel : ViewModel(), KoinComponent {
    private val drinks = DrinkService()
    val latestDrinks = mutableStateListOf<LatestDrinkInfo>()

    suspend fun loadDrinks() {
        val d = drinks.getLatestDrinks(15)
        latestDrinks.clear()
        latestDrinks.addAll(d)
    }
}

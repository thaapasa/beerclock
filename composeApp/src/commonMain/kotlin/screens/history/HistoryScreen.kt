package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object HistoryScreen : Screen {

    @Composable
    override fun Content() {
        val db = LocalDatabase.current
        val vm = rememberWithDispose { HistoryScreenViewModel(DrinkService(db)) }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                vm.loadTodaysDrinks()
            }
        }

        return MainLayout(
            content = { innerPadding ->
                DrinkList(vm.drinks,
                    modifier = Modifier.padding(innerPadding),
                    onClick = { vm.deleteDrink(it) }
                )
            },
        )
    }
}

class HistoryScreenViewModel(private val drinkService: DrinkService) : ViewModel() {
    val drinks = mutableStateListOf<DrinkRecordInfo>()

    fun loadTodaysDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForToday()
            drinks.addAll(newDrinks)
        }
    }

    fun deleteDrink(drink: DrinkRecordInfo) {
        launch {
            drinkService.deleteDrinkById(drink.id)
            drinks.remove(drink)
        }
    }
}

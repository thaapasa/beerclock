package fi.tuska.beerclock.screens.today

import androidx.compose.runtime.mutableStateListOf
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val drinkService: DrinkService) : ViewModel() {
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

package fi.tuska.beerclock.screens.today

import androidx.compose.runtime.mutableStateListOf
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch

private val logger = getLogger("HomeViewModel")

class HomeViewModel : ViewModel() {
    private val drinkService = DrinkService()
    val drinks = mutableStateListOf<DrinkRecordInfo>()

    fun units(): Double = drinks.sumOf { it.units() }

    fun loadTodaysDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForToday()
            drinks.addAll(newDrinks)
        }
    }

    fun reload() {
        logger.info("Reloading drinks")
        loadTodaysDrinks()
    }
}

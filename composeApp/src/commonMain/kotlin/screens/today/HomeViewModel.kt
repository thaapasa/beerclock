package fi.tuska.beerclock.screens.today

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacCalculation
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch

private val logger = getLogger("HomeViewModel")

class HomeViewModel : ViewModel() {
    private val drinkService = DrinkService()
    private val times = DrinkTimeService()
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    var bac by mutableStateOf(BacCalculation(drinks))
        private set

    fun units(): Double = drinks.sumOf { it.units() }

    fun loadTodaysDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForHomeScreen()
            val dayStart = times.dayStartTime()
            bac = BacCalculation(newDrinks)
            drinks.addAll(newDrinks.filter { it.time >= dayStart })
        }
    }

    fun reload() {
        logger.info("Reloading drinks")
        loadTodaysDrinks()
    }
}

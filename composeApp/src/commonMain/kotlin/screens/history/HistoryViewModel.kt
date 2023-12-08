package fi.tuska.beerclock.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.screens.drinks.modify.EditDrinkDialog
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class HistoryViewModel : ViewModel() {
    private val times = DrinkTimeService()
    private val drinkService = DrinkService()
    val drinks = mutableStateListOf<DrinkRecordInfo>()

    var date by mutableStateOf(times.currentDrinkDay())
        private set

    private var editingDrink by mutableStateOf<DrinkRecordInfo?>(null)

    fun loadDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForDay(date)
            drinks.addAll(newDrinks)
        }
    }

    fun selectDay(day: LocalDate) {
        date = day
        loadDrinks()
    }

    fun nextDay() = selectDay(date.plus(1, DateTimeUnit.DAY))
    fun prevDay() = selectDay(date.minus(1, DateTimeUnit.DAY))

    fun deleteDrink(drink: DrinkRecordInfo) {
        launch {
            drinkService.deleteDrinkById(drink.id)
            drinks.remove(drink)
        }
    }

    fun modifyDrink(drink: DrinkRecordInfo) {
        editingDrink = drink
    }

    @Composable
    fun EditDialog() {
        val drink = editingDrink
        if (drink != null) {
            EditDrinkDialog(drink, onClose = {
                editingDrink = null
                loadDrinks()
            })
        }
    }
}

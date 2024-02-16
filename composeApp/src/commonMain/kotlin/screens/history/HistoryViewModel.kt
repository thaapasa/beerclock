package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.screens.drinks.modify.EditDrinkDialog
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.components.BacStatusViewModel
import fi.tuska.beerclock.ui.components.DateView
import fi.tuska.beerclock.ui.components.GaugeValue
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HistoryViewModel(atDate: LocalDate?, private val navigator: Navigator) :
    SnackbarViewModel(SnackbarHostState()),
    BacStatusViewModel,
    KoinComponent {
    private val times = DrinkTimeService()
    private val drinkService = DrinkService()
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    private val prefs: GlobalUserPreferences = get()

    val date = atDate ?: times.currentDrinkDay()

    private val dailyUnitsGauge =
        GaugeValue(appIcon = AppIcon.DRINK, maxValue = prefs.prefs.maxDailyUnits)
    private val weeklyUnitsGauge =
        GaugeValue(appIcon = AppIcon.CALENDAR_WEEK, maxValue = prefs.prefs.maxWeeklyUnits)

    override val gauges = listOf(dailyUnitsGauge, weeklyUnitsGauge)

    @Composable
    override fun Content() {
        DateView(date, modifier = Modifier.padding(16.dp))
    }

    private var editingDrink by mutableStateOf<DrinkRecordInfo?>(null)

    fun loadDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForDay(date).reversed()
            drinks.addAll(newDrinks)
            updateStatus()
        }
    }

    private suspend fun updateStatus() {
        val weekUnits = drinkService.getUnitsForWeek(date, prefs.prefs)
        dailyUnitsGauge.setValue(drinks.sumOf { it.units() }, prefs.prefs.maxDailyUnits)
        weeklyUnitsGauge.setValue(weekUnits, prefs.prefs.maxWeeklyUnits)
    }

    fun selectDay(date: LocalDate) = navigator.replace(HistoryScreen(date))

    fun nextDay() = selectDay(date.plus(1, DateTimeUnit.DAY))
    fun prevDay() = selectDay(date.minus(1, DateTimeUnit.DAY))

    fun deleteDrink(drink: DrinkRecordInfo) {
        launch {
            drinkService.deleteDrinkById(drink.id)
            drinks.remove(drink)
            updateStatus()
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

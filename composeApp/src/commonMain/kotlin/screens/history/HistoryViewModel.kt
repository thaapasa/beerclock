package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.modify.EditDrinkDialog
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.components.BacStatusViewModel
import fi.tuska.beerclock.ui.components.DateView
import fi.tuska.beerclock.ui.components.GaugeValueWithHelp
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import fi.tuska.beerclock.util.DataState
import fi.tuska.beerclock.util.SuspendAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("HistoryViewModel")

class HistoryViewModel(
    atDate: LocalDate?,
    initAction: SuspendAction<HistoryViewModel>? = null,
    initialDailyGaugeValue: Double = 0.0,
    initialWeeklyGaugeValue: Double = 0.0,
    private val navigator: Navigator,
) :
    SnackbarViewModel(SnackbarHostState()),
    BacStatusViewModel,
    KoinComponent {
    private val times = DrinkTimeService()
    private val drinkService = DrinkService()

    private val prefs: GlobalUserPreferences = get()
    private val strings = Strings.get()

    val date = atDate ?: times.currentDrinkDay()
    val drinks: StateFlow<DataState<List<DrinkRecordInfo>>> =
        drinkService.flowDrinksForDay(date).map { DataState.Success(it) }
            .stateIn(
                scope = this,
                initialValue = DataState.Initial,
                started = SharingStarted.WhileSubscribed(5_000)
            )

    val weeklyUnits: StateFlow<Double> = drinkService.flowUnitsForWeek(date, prefs.prefs).stateIn(
        scope = this,
        initialValue = initialWeeklyGaugeValue,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private val dailyUnitsGauge =
        GaugeValueWithHelp(
            initialDailyGaugeValue,
            appIcon = AppIcon.DRINK,
            maxValue = prefs.prefs.maxDailyUnits,
            helpText = strings.help.dailyUnitsGauge,
        )
    private val weeklyUnitsGauge =
        GaugeValueWithHelp(
            initialWeeklyGaugeValue,
            appIcon = AppIcon.CALENDAR_WEEK,
            maxValue = prefs.prefs.maxWeeklyUnits,
            helpText = strings.help.weeklyUnitsGauge,
        )

    override val gauges = listOf(dailyUnitsGauge, weeklyUnitsGauge)

    init {
        initAction?.let { launch { it(this@HistoryViewModel) } }
        launch {
            weeklyUnits.collect {
                weeklyUnitsGauge.setValue(it, prefs.prefs.maxWeeklyUnits)
            }
        }
        launch {
            drinks.collect { drinks ->
                dailyUnitsGauge.setValue(
                    drinks.mapOr({ it.sumOf { it.units() } }, initialDailyGaugeValue),
                    prefs.prefs.maxDailyUnits
                )
            }
        }
    }

    @Composable
    override fun Content() {
        DateView(date, modifier = Modifier.padding(16.dp))
    }

    suspend fun showDrinkAdded(drink: DrinkRecordInfo) {
        withContext(Dispatchers.Main) {
            val result =
                snackbar.showSnackbar(
                    strings.history.drinkAdded(drink),
                    actionLabel = strings.remove,
                    duration = SnackbarDuration.Short
                )
            if (result == SnackbarResult.ActionPerformed) {
                // Remove added drink
                withContext(Dispatchers.IO) {
                    drinkService.deleteDrinkById(drink.id)
                }
            }
        }
    }

    suspend fun showDrinkDeleted(drink: DrinkRecordInfo) {
        withContext(Dispatchers.Main) {
            val result =
                snackbar.showSnackbar(
                    strings.history.drinkDeleted(drink),
                    actionLabel = strings.cancel,
                    duration = SnackbarDuration.Short
                )
            if (result == SnackbarResult.ActionPerformed) {
                // Remove added drink
                withContext(Dispatchers.IO) {
                    val restored =
                        drinkService.insertDrink(DrinkDetailsFromEditor.fromRecord(drink))
                    logger.info("Restored $restored to db")
                }
            }
        }
    }

    private var editingDrink by mutableStateOf<DrinkRecordInfo?>(null)

    fun selectDay(date: LocalDate) = navigator.replace(
        HistoryScreen(
            date,
            initialDailyGaugeValue = dailyUnitsGauge.value,
            initialWeeklyGaugeValue = weeklyUnitsGauge.value
        )
    )

    fun nextDay() = selectDay(date.plus(1, DateTimeUnit.DAY))
    fun prevDay() = selectDay(date.minus(1, DateTimeUnit.DAY))

    fun deleteDrink(drink: DrinkRecordInfo) {
        launch {
            drinkService.deleteDrinkById(drink.id)
            showDrinkDeleted(drink)
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
            })
        }
    }
}

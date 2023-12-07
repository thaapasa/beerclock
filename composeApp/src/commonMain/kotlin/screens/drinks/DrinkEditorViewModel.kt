package fi.tuska.beerclock.screens.drinks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.NewDrinkRecord
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

open class DrinkEditorViewModel : ViewModel(), KoinComponent {
    protected val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()

    private val drinkTime = times.instantToDrinkTime(Clock.System.now())

    val drinks = mutableStateListOf<DrinkRecord>()
    var name by mutableStateOf("")
    var abv by mutableStateOf(4.5)
    var quantityCl by mutableStateOf(33.0)
    var date by mutableStateOf(drinkTime.first)
    var time by mutableStateOf(drinkTime.second)
    var image by mutableStateOf(DrinkImage.GENERIC_DRINK)
    var isSaving by mutableStateOf(false)

    fun realTime(): Instant = times.drinkTimeToInstant(date, time)
    fun localRealTime() = times.toLocalDateTime(realTime())
    fun units(): Double = BacFormulas.getUnitsFromDisplayQuantityAbv(
        quantityCl = quantityCl,
        abvPercentage = abv,
        prefs = prefs.prefs
    )

    protected fun toNewDrinkRecord(): NewDrinkRecord {
        return NewDrinkRecord(
            time = realTime(),
            name = name,
            abv = abv / 100.0,
            quantityLiters = quantityCl / 100,
            image = image,
        )
    }

    protected fun savingAction(action: suspend (scope: CoroutineScope) -> Unit) {
        launch {
            if (isSaving) return@launch
            isSaving = true
            try {
                action(this)
            } finally {
                isSaving = false
            }
        }
    }
}

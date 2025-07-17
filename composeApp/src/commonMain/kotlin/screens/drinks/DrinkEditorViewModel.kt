package fi.tuska.beerclock.screens.drinks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.events.EventBus
import fi.tuska.beerclock.images.toDrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.time.Clock
import kotlin.time.Instant

private val defaultDrink = BasicDrinkInfo.default

open class DrinkEditorViewModel : ViewModel(), KoinComponent {
    protected val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()

    val drinks = mutableStateListOf<DrinkRecord>()
    val eventBus: EventBus = get()

    var producer by mutableStateOf("")
    var name by mutableStateOf("")
    var note by mutableStateOf("")
    var rating by mutableStateOf<Double?>(null)
    var abv by mutableStateOf(defaultDrink.abvPercentage)
    var quantityCl by mutableStateOf(defaultDrink.quantityCl)
    var date by mutableStateOf(LocalDate(2000, 1, 1))
    var time by mutableStateOf(LocalTime(0, 0, 0))
    var image by mutableStateOf(defaultDrink.image)
    var category by mutableStateOf<Category?>(null)
    var isSaving by mutableStateOf(false)

    fun realTime(): Instant = times.drinkTimeToInstant(date, time)
    fun localRealTime() = times.toLocalDateTime(realTime())
    fun isInFuture(now: Instant = Clock.System.now()): Boolean {
        val drinkTime = times.drinkTimeToInstant(date, time)
        return drinkTime > now
    }

    fun units(): Double = BacFormulas.getUnitsFromDisplayQuantityAbv(
        quantityCl = quantityCl,
        abvPercentage = abv,
        prefs = prefs.prefs
    )

    fun isValid(now: Instant = Clock.System.now()): Boolean {
        return name.isNotBlank() && quantityCl > 0.0 && !isInFuture(now)
    }

    protected fun setValues(drink: BasicDrinkInfo, realTime: Instant = Clock.System.now()) {
        producer = drink.producer
        name = drink.name
        quantityCl = drink.quantityCl
        abv = drink.abvPercentage
        image = drink.image.toDrinkImage()
        note = drink.note ?: ""
        category = drink.category
        rating = drink.rating
        val drinkTime = times.instantToDrinkTime(realTime)
        date = drinkTime.first
        time = drinkTime.second
    }

    protected fun toSaveDetails(): DrinkDetailsFromEditor {
        return DrinkDetailsFromEditor(
            time = realTime(),
            producer = producer,
            name = name,
            abv = abv / 100.0,
            quantityLiters = quantityCl / 100,
            image = image,
            category = category,
            rating = rating,
            note = note.ifBlank { null },
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

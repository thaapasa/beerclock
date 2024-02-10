package fi.tuska.beerclock.screens.newdrink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkDef
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.history.HistoryScreen
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import fi.tuska.beerclock.screens.drinks.create.NewDrinkViewModel as CreateNewDrinkViewModel

private val logger = getLogger("NewDrinkViewModel")

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NewDrinkViewModel(private val navigator: Navigator, private val date: LocalDate?) :
    ViewModel(), KoinComponent {
    private val drinks = DrinkService()

    var searchQuery by mutableStateOf("")
    private val strings = Strings.get()
    var active by mutableStateOf(true)
        private set

    fun toggleActive(state: Boolean) {
        this.active = state
        if (!state) {
            navigator.pop()
        }
    }

    val searchResults: StateFlow<List<BasicDrinkInfo>> =
        snapshotFlow { searchQuery }
            // Do not debounce for empty string to get latest drinks list ASAP
            .debounce { if (it.isEmpty()) 0 else 300 }
            .flatMapLatest {
                when {
                    it.isNotBlank() -> flowMatchingDrinks(it)
                    else -> flowLatestDrinks()
                }
            }.stateIn(
                scope = this,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    var dialogOpen by mutableStateOf(false)
        private set
    var proto: BasicDrinkInfo? = null

    private inline fun isBusy() = dialogOpen

    fun selectDrink(drink: BasicDrinkInfo?) {
        if (isBusy()) return
        if (drink == null) {
            editDrink(null)
            return
        } else {
            drinkDrink(drink)
        }
    }

    fun editDrink(drink: BasicDrinkInfo?) {
        if (isBusy()) return
        logger.info("Opening for editing: $drink")
        proto = drink
        dialogOpen = true
    }

    fun closeDialog() {
        dialogOpen = false
    }

    fun returnToHome() {
        if (date == null) {
            // Go back to home screen
            navigator.replaceAll(HomeScreen)
        } else {
            // Go back to history screen for the given date
            navigator.replaceAll(HistoryScreen(date))
        }
    }

    private fun drinkDrink(drink: BasicDrinkInfo) {
        launch {
            logger.info("Drinking: $drink")
            val dvm = CreateNewDrinkViewModel(drink, date)
            dvm.addDrink {
                returnToHome()
            }
        }
    }

    private fun flowMatchingDrinks(drinkName: String): Flow<List<BasicDrinkInfo>> =
        drinks.flowMatchingDrinksByName(drinkName, 10).map { matching ->
            val header = listOf(TextDrinkInfo(
                "add-new-title",
                strings.newdrink.addNewDrink(drinkName),
                icon = AppIcon.ADD_CIRCLE,
                onClick = { selectDrink(DrinkDef(name = drinkName)) }
            ))
            if (matching.isEmpty()) {
                if (!drinks.libraryHasDrinks()) {
                    return@map header + TextDrinkInfo(
                        "empty-library-note",
                        strings.newdrink.emptyLibraryTitle,
                        description = strings.newdrink.emptyLibraryDescription,
                        icon = AppIcon.DRINK,
                        onClick = { addExampleDrinks() }
                    )
                }
            }
            return@map header + matching
        }


    private fun addExampleDrinks() {
        launch {
            drinks.addExampleDrinks()
        }
    }

    private fun flowLatestDrinks() = flow {
        val latest = drinks.getLatestDrinks(15)
        emit(
            listOf(
                TextDrinkInfo(
                    "latest-title",
                    strings.newdrink.latestDrinksTitle,
                    icon = AppIcon.ADD_CIRCLE,
                    onClick = { selectDrink(null) }
                )
            ) + latest
        )
    }

}

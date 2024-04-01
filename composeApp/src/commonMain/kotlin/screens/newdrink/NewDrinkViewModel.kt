package fi.tuska.beerclock.screens.newdrink

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkDef
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.events.DrinkRecordAddedEvent
import fi.tuska.beerclock.events.EventBus
import fi.tuska.beerclock.events.EventObserver
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.create.AddDrinkScreen
import fi.tuska.beerclock.screens.library.DrinkLibraryViewModel
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
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
import org.koin.core.component.get

private val logger = getLogger("NewDrinkViewModel")

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NewDrinkViewModel(
    private val navigator: Navigator,
    private val date: LocalDate?,
    searchString: String? = null,
    private val updateSearch: (query: String) -> Unit,
) : SnackbarViewModel(SnackbarHostState()), KoinComponent {
    private val drinks = DrinkService()
    private val libraryVm = DrinkLibraryViewModel(navigator, snackbar = snackbar)
    private val eventBus: EventBus = get()

    override fun onDispose() {
        super.onDispose()
        libraryVm.onDispose()
    }

    init {
        // Navigate back when a new drink is successfully recorded
        EventObserver(this).observeEventsOfType<DrinkRecordAddedEvent>(consumeEvent = false) {
            navigator.pop()
        }
    }

    var searchQuery by mutableStateOf(searchString ?: "")
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

    fun deleteDrinkInfo(drink: DrinkInfo) {
        libraryVm.deleteDrink(drink)
    }

    fun modifyDrinkInfo(drink: DrinkInfo) {
        updateSearch(searchQuery)
        libraryVm.editDrink(drink)
    }

    @Composable
    fun DrinkInfoDialog() {
        libraryVm.InfoDialog()
    }

    fun selectDrink(drink: BasicDrinkInfo?) {
        if (drink == null) {
            editDrink(null)
            return
        } else {
            drinkDrink(drink)
        }
    }

    fun editDrink(drink: BasicDrinkInfo?) {
        logger.info("Opening for editing: $drink")
        updateSearch(searchQuery)
        navigator.push(AddDrinkScreen(date, proto = drink))
    }

    private fun drinkDrink(drink: BasicDrinkInfo) {
        launch {
            logger.info("Drinking drink: $drink")
            val d = drinks.insertDrink(DrinkDetailsFromEditor.fromBasicInfo(drink, date))
            eventBus.post(DrinkRecordAddedEvent(d))
            navigator.pop()
        }
    }

    private fun flowMatchingDrinks(drinkName: String): Flow<List<BasicDrinkInfo>> =
        drinks.flowMatchingDrinksByName(drinkName, 10).map { matching ->
            val header = listOf(TextDrinkInfo(
                "add-new-title",
                strings.newdrink.addNewDrink(drinkName),
                icon = AppIcon.ADD_CIRCLE,
                onClick = { editDrink(DrinkDef(name = drinkName)) }
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

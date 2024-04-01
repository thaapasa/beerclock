package fi.tuska.beerclock.screens.library

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.events.DrinkInfoAddedEvent
import fi.tuska.beerclock.events.DrinkInfoDeletedEvent
import fi.tuska.beerclock.events.DrinkInfoUpdatedEvent
import fi.tuska.beerclock.events.EventBus
import fi.tuska.beerclock.events.EventObserver
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.library.create.CreateDrinkInfoScreen
import fi.tuska.beerclock.screens.library.modify.EditDrinkInfoScreen
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("DrinkLibraryViewModel")

@OptIn(ExperimentalCoroutinesApi::class)
class DrinkLibraryViewModel(
    private val navigator: Navigator,
    initialCategory: Category? = null,
    private val updateCategory: ((cat: Category?) -> Unit)? = null,
    snackbar: SnackbarHostState? = null,
) :
    SnackbarViewModel(snackbar ?: SnackbarHostState()), KoinComponent {
    private val drinks = DrinkService()
    private val eventBus: EventBus = get()
    var selectedCategory by mutableStateOf<Category?>(initialCategory)
        private set

    private var viewingDrink by mutableStateOf<DrinkInfo?>(null)

    init {
        val observer = EventObserver(this)
        observer.observeEventsOfType<DrinkInfoDeletedEvent> { showDrinkDeleted(it.drink) }
        observer.observeEventsOfType<DrinkInfoAddedEvent> { }
        observer.observeEventsOfType<DrinkInfoUpdatedEvent> { }
    }

    fun categoryHeaderInfo(): CategoryHeaderInfo {
        val cat = selectedCategory
        return CategoryHeaderInfo(
            cat,
            Strings.get().drink.categoryName(cat)
        )
    }

    val libraryResults: StateFlow<List<DrinkInfo>> =
        snapshotFlow { selectedCategory }
            .flatMapLatest {
                drinks.flowDrinksForCategories(it)
            }.stateIn(
                scope = this,
                initialValue = listOf(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    val drinkDetails: StateFlow<DrinkDetails?> =
        snapshotFlow { viewingDrink }.flatMapLatest { drinks.flowDrinkDetails(it) }.stateIn(
            scope = this,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun selectCategory(category: Category?) {
        logger.info("Selecting category $category")
        selectedCategory = category
    }

    fun addExampleDrinks() {
        launch {
            drinks.addExampleDrinks()
            showMessage(Strings.get().library.defaultDrinksAdded)
        }
    }

    fun addNewDrink() {
        this.viewingDrink = null
        logger.info("Adding new drink")
        navigator.push(CreateDrinkInfoScreen)
    }

    fun viewDrink(drink: DrinkInfo) {
        this.viewingDrink = drink
    }

    fun editDrink(drink: DrinkInfo) {
        logger.info("Editing drink $drink")
        this.viewingDrink = null
        updateCategory?.invoke(selectedCategory)
        navigator.push(EditDrinkInfoScreen(drink))
    }

    fun deleteDrink(drink: DrinkInfo) {
        launch {
            drinks.deleteDrinkInfoById(drink.id)
            eventBus.post(DrinkInfoDeletedEvent(drink))
        }
    }


    private fun showDrinkDeleted(drink: DrinkInfo) {
        val strings = Strings.get()
        launch(Dispatchers.Main) {
            val result =
                snackbar.showSnackbar(
                    strings.library.drinkDeleted(drink),
                    actionLabel = strings.cancel,
                    duration = SnackbarDuration.Short
                )
            if (result == SnackbarResult.ActionPerformed) {
                // Remove added drink
                withContext(Dispatchers.IO) {
                    val restored =
                        drinks.insertDrinkInfo(
                            DrinkDetailsFromEditor.fromBasicInfo(
                                drink,
                                Clock.System.now()
                            )
                        )
                    logger.info("Restored $restored to db")
                }
            }
        }
    }


    private fun closeView() {
        this.viewingDrink = null
    }

    @Composable
    fun InfoDialog() {
        val viewDrink = viewingDrink
        val details by drinkDetails.collectAsState()
        if (viewDrink != null) {
            DrinkItemInfoDialog(
                viewDrink,
                details,
                onClose = this::closeView,
                onModify = this::editDrink,
                onDelete = this::deleteDrink
            )
        }
    }
}

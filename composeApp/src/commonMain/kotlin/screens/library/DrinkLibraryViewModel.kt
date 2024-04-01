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
import fi.tuska.beerclock.drinks.BasicDrinkInfo
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
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.library.create.CreateDrinkInfoScreen
import fi.tuska.beerclock.screens.library.modify.EditDrinkInfoScreen
import fi.tuska.beerclock.screens.newdrink.TextDrinkInfo
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun getFalse() = false

private val logger = getLogger("DrinkLibraryViewModel")

@OptIn(ExperimentalCoroutinesApi::class)
class DrinkLibraryViewModel(private val navigator: Navigator, snackbar: SnackbarHostState? = null) :
    SnackbarViewModel(snackbar ?: SnackbarHostState()), KoinComponent {
    private val drinks = DrinkService()
    private val eventBus: EventBus = get()
    var selections by mutableStateOf<Map<Category, Boolean>>(mapOf())
        private set

    private var viewingDrink by mutableStateOf<DrinkInfo?>(null)

    init {
        val observer = EventObserver(this)
        observer.observeEventsOfType<DrinkInfoDeletedEvent> { showDrinkDeleted(it.drink) }
        observer.observeEventsOfType<DrinkInfoAddedEvent> { }
        observer.observeEventsOfType<DrinkInfoUpdatedEvent> { }
    }

    private val exampleDrinksItem = TextDrinkInfo(
        key = "default-drinks",
        name = Strings.get().library.addDefaultDrinks,
        icon = AppIcon.DRINK,
        onClick = this::addExampleDrinks
    )

    val libraryResults: StateFlow<List<BasicDrinkInfo>> =
        snapshotFlow { selections }
            .flatMapLatest {
                drinks.flowDrinksForCategories(it.keys)
            }.map {
                it.partitionByCategory().toListWithHeaders() + listOf(exampleDrinksItem)
            }.stateIn(
                scope = this,
                initialValue = listOf(exampleDrinksItem),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    val drinkDetails: StateFlow<DrinkDetails?> =
        snapshotFlow { viewingDrink }.flatMapLatest { drinks.flowDrinkDetails(it) }.stateIn(
            scope = this,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun selectedCategories(): Set<Category> = selections.keys
    fun toggleCategory(category: Category) {
        logger.info("Toggling category $category")
        selections = if (selections.getOrElse(category, ::getFalse)) {
            selections - category
        } else {
            selections + mapOf(category to true)
        }
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

data class DrinkGroup(val category: Category?, val drinks: List<BasicDrinkInfo>)

fun List<BasicDrinkInfo>.partitionByCategory(): Map<Category?, DrinkGroup> {
    return groupBy { it.category }.mapValues { DrinkGroup(category = it.key, drinks = it.value) }
}

fun Map<Category?, DrinkGroup>.toListWithHeaders(): List<BasicDrinkInfo> {
    val strings = Strings.get()
    return values.sortedBy { it.category?.order ?: Int.MAX_VALUE }.map {
        if (it.drinks.isNotEmpty()) {
            listOf<BasicDrinkInfo>(
                CategoryHeaderInfo(it.category, strings.drink.categoryName(it.category))
            ) + it.drinks
        } else listOf()
    }.flatten()
}

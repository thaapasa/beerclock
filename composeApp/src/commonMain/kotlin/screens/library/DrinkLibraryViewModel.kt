package fi.tuska.beerclock.screens.library

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import fi.tuska.beerclock.database.DrinkLibrary
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.library.create.CreateDrinkInfoDialog
import fi.tuska.beerclock.screens.library.modify.EditDrinkInfoDialog
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

fun getFalse() = false


private val logger = getLogger("DrinkLibraryViewModel")
private val NewDrink =
    DrinkInfo(
        DrinkLibrary(
            id = 0,
            name = "",
            category = null,
            abv = 10.0,
            quantity_liters = 0.5,
            drink_count = 0,
            image = DrinkImage.GENERIC_DRINK.name,
            created = Clock.System.now().toDbTime(),
            updated = Clock.System.now().toDbTime(),
        )
    )

@OptIn(ExperimentalCoroutinesApi::class)
class DrinkLibraryViewModel : SnackbarViewModel(SnackbarHostState()), KoinComponent {
    private val drinks = DrinkService()
    var selections by mutableStateOf<Map<Category, Boolean>>(mapOf())
        private set

    private var viewingDrink by mutableStateOf<DrinkInfo?>(null)
    private var editingDrink by mutableStateOf<DrinkInfo?>(null)

    val libraryResults: StateFlow<List<BasicDrinkInfo>> =
        snapshotFlow { selections }
            .flatMapLatest {
                drinks.flowDrinksForCategories(it.keys)
            }.map {
                it.partitionByCategory().toListWithHeaders()
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

    inline fun selectedCategories(): Set<Category> = selections.keys
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
        }
    }

    fun addNewDrink() {
        this.viewingDrink = null
        this.editingDrink = NewDrink
    }

    fun viewDrink(drink: DrinkInfo) {
        this.viewingDrink = drink
        this.editingDrink = null
    }

    private fun editDrink(drink: DrinkInfo) {
        this.viewingDrink = null
        this.editingDrink = drink
    }

    private fun deleteDrink(drink: DrinkInfo) {
        launch {
            drinks.deleteDrinkInfoById(drink.id)
        }
        closeEdit()
    }

    private fun closeEdit() {
        this.editingDrink = null
    }

    private fun closeView() {
        this.viewingDrink = null
    }

    @Composable
    fun EditorDialog() {
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
        val drink = editingDrink
        if (drink != null) {
            if (drink == NewDrink) {
                CreateDrinkInfoDialog(onClose = this::closeEdit)
            } else {
                EditDrinkInfoDialog(drink, onClose = this::closeEdit)
            }
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

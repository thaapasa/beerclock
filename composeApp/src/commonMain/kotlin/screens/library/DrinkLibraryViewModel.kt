package fi.tuska.beerclock.screens.library

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshotFlow
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

fun getFalse() = false

class DrinkLibraryViewModel : ViewModel(), KoinComponent {
    private val drinks = DrinkService()

    val selections = mutableStateMapOf<Category, Boolean>()

    val libraryResults: StateFlow<List<BasicDrinkInfo>> =
        snapshotFlow { selections }
            .flatMapLatest {
                drinks.flowDrinksForCategories(it.keys)
            }.stateIn(
                scope = this,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    inline fun selectedCategories(): Set<Category> = selections.keys
    inline fun toggleCategory(category: Category) {
        if (selections.getOrElse(category, ::getFalse)) {
            selections.remove(category)
        } else {
            selections[category] = true
        }
    }

}

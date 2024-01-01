package fi.tuska.beerclock.screens.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

fun getFalse() = false


private val logger = getLogger("DrinkLibraryViewModel")

class DrinkLibraryViewModel : ViewModel(), KoinComponent {
    private val drinks = DrinkService()

    var selections by mutableStateOf<Map<Category, Boolean>>(mapOf())
        private set

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

    inline fun selectedCategories(): Set<Category> = selections.keys
    fun toggleCategory(category: Category) {
        logger.info("Toggling category $category")
        selections = if (selections.getOrElse(category, ::getFalse)) {
            selections - category
        } else {
            selections + mapOf(category to true)
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

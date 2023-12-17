package fi.tuska.beerclock.screens.newdrink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NewDrinkViewModel : ViewModel(), KoinComponent {
    private val drinks = DrinkService()

    var searchQuery by mutableStateOf("")
    private val strings = Strings.get()

    val searchResults: StateFlow<List<BasicDrinkInfo>> =
        snapshotFlow { searchQuery }
            .debounce(300)
            .flatMapLatest {
                when {
                    it.isNotBlank() -> flow { emit(drinks.findMatchingDrinksByName(it, 10)) }
                    else -> flow {
                        val latest = drinks.getLatestDrinks(15)
                        emit(
                            listOf(
                                TextDrinkInfo(
                                    "list-title",
                                    strings.newdrink.latestDrinksTitle
                                )
                            ) + latest
                        )
                    }
                }
            }.stateIn(
                scope = this,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    var dialogOpen by mutableStateOf(false)
        private set
    var proto: BasicDrinkInfo? = null

    fun selectDrink(latest: BasicDrinkInfo?) {
        if (dialogOpen) {
            return
        }
        proto = latest
        dialogOpen = true
    }

    fun closeDialog() {
        dialogOpen = false
    }
}

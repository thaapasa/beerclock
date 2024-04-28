package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.ui.components.AppDialog
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MixedDrinksViewModel : SnackbarViewModel(snackbar = SnackbarHostState()) {
    private val mixService = MixedDrinksService()

    var editingMix by mutableStateOf<MixedDrinkInfo?>(null)

    val mixedDrinkResults: StateFlow<List<MixedDrinkInfo>> =
        mixService.flowMixedDrinks()
            .stateIn(
                scope = this,
                initialValue = listOf(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    @Composable
    fun EditorDialog() {
        val mix = editingMix ?: return
        AppDialog(onClose = ::dismissEditor) {
            val vm = rememberWithDispose { MixedDrinkEditorViewModel(mix) }
            MixedDrinkEditor(vm, onClose = ::dismissEditor)
        }
    }

    private fun dismissEditor() {
        this.editingMix = null
    }

    fun addNewMix() {
        this.editingMix = MixedDrinkInfo(name = "")
    }

    fun modifyMix(drinkMix: MixedDrinkInfo) {
        this.editingMix = drinkMix
    }

    fun deleteMix(drinkMix: MixedDrinkInfo) {
        val mixId = drinkMix.id ?: return
        launch {
            mixService.deleteDrinkMix(mixId)
        }
    }

}

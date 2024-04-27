package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.ui.components.AppDialog
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose

class MixedDrinksViewModel : SnackbarViewModel(snackbar = SnackbarHostState()) {
    var editingMix by mutableStateOf<MixedDrinkInfo?>(null)

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

}

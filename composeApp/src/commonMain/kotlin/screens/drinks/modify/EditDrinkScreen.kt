package fi.tuska.beerclock.screens.drinks.modify

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.drinks.DrinkDialogLayout
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.util.CommonParcelize

@CommonParcelize
data class EditDrinkScreen(val drink: DrinkRecordInfo) : ParcelableScreen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose { EditDrinkViewModel(drink, navigator) }
        val strings = Strings.get()

        DrinkDialogLayout(title = strings.drinkDialog.modifyTitle, saveButton = { modifier ->
            TextButton(
                enabled = !vm.isSaving && vm.isValid(),
                onClick = {
                    vm.saveDrink()
                },
                modifier = modifier.padding(end = 8.dp)
            ) { Text(strings.drinkDialog.submit) }
        }) {
            DrinkEditor(vm)
        }
    }
}

package fi.tuska.beerclock.screens.drinks.create

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.drinks.DrinkDialogLayout
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.util.CommonParcelize
import fi.tuska.beerclock.util.CommonTypeParceler
import fi.tuska.beerclock.util.LocalDateParceler
import kotlinx.datetime.LocalDate

@CommonParcelize
data class AddDrinkScreen(
    @CommonTypeParceler<LocalDate?, LocalDateParceler>()
    private val date: LocalDate? = null,
    private val proto: BasicDrinkInfo? = null,
) : ParcelableScreen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose { NewDrinkViewModel(proto, date, navigator) }
        val strings = Strings.get()

        DrinkDialogLayout(title = strings.drinkDialog.createTitle, saveButton = { modifier ->
            TextButton(
                enabled = !vm.isSaving && vm.isValid(),
                onClick = vm::addDrink,
                modifier = modifier.padding(end = 8.dp)
            ) { Text(strings.drinkDialog.submit) }
        }) {
            DrinkEditor(vm)
        }
    }
}


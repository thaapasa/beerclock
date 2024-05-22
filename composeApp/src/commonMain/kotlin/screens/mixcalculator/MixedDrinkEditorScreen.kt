package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.drinks.DrinkDialogLayout
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.util.CommonParcelize

val emptyItem = MixedDrink(MixedDrinkInfo(name = ""), items = listOf())

@CommonParcelize
data class MixedDrinkEditorScreen(val proto: MixedDrink? = null) : ParcelableScreen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose {
            MixedDrinkEditorViewModel(proto ?: emptyItem)
        }

        val strings = Strings.get()
        DrinkDialogLayout(
            title = if (vm.isNewMix) strings.mixedDrinks.newMixTitle else strings.mixedDrinks.editMixTitle,
            saveButton = { modifier ->
                TextButton(
                    enabled = true,
                    onClick = { vm.save(andThen = { navigator.pop() }) },
                    modifier = modifier.padding(end = 8.dp)
                ) { Text(strings.drinkDialog.submit) }
            }) {
            MixedDrinkEditor(vm, onClose = { navigator.pop() })
        }
    }
}

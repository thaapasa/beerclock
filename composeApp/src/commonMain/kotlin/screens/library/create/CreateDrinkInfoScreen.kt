package fi.tuska.beerclock.screens.library.create

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkDialogLayout
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.composables.rememberWithDispose

object CreateDrinkInfoScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose { CreateDrinkInfoViewModel(navigator) }
        val strings = Strings.get()

        DrinkDialogLayout(title = strings.library.newDrinkTitle, saveButton = { modifier ->
            TextButton(
                enabled = !vm.isSaving && vm.isValid(),
                onClick = vm::saveDrink,
                modifier = modifier.padding(end = 8.dp)
            ) { Text(strings.library.saveDrinkTitle) }
        }) {
            DrinkEditor(vm, showTime = false)
        }
    }

}

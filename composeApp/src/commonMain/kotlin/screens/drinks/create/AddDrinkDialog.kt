package fi.tuska.beerclock.screens.drinks.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkAction
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.components.FullScreenDialog
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import kotlinx.datetime.LocalDate

@Composable
fun AddDrinkDialog(
    date: LocalDate? = null,
    proto: BasicDrinkInfo? = null,
    onSelectDrink: DrinkAction,
    onClose: () -> Unit,
) {
    val vm = rememberWithDispose { NewDrinkViewModel(proto, date, onSelectDrink) }
    val strings = Strings.get()
    val scrollState = rememberScrollState()

    FullScreenDialog(onDismissRequest = onClose) {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            DialogHeader(
                titleText = strings.drinkDialog.createTitle,
                leadingIcon = { modifier ->
                    AppIcon.CLOSE.iconButton(
                        onClick = onClose,
                        modifier = modifier,
                        contentDescription = strings.dialogClose
                    )
                },
                textButton = { modifier ->
                    TextButton(
                        enabled = !vm.isSaving && vm.isValid(),
                        onClick = vm::addDrink,
                        modifier = modifier
                    ) { Text(strings.drinkDialog.submit) }
                }
            )
            DrinkEditor(vm)
        }
    }
}

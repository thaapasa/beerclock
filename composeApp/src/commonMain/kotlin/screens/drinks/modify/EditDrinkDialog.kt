package fi.tuska.beerclock.screens.drinks.modify

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.components.FullScreenDialog
import fi.tuska.beerclock.ui.composables.rememberWithDispose

@Composable
fun EditDrinkDialog(
    drink: DrinkRecordInfo,
    onDrinksUpdated: (() -> Unit)? = null,
    onClose: () -> Unit,
) {
    val vm = rememberWithDispose { EditDrinkViewModel(drink) }
    val strings = Strings.get()
    val scrollState = rememberScrollState()

    FullScreenDialog(onDismissRequest = onClose) {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            DialogHeader(
                titleText = strings.drinkDialog.modifyTitle,
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
                        onClick = {
                            vm.saveDrink {
                                onDrinksUpdated?.invoke()
                                onClose()
                            }
                        },
                        modifier = modifier
                    ) { Text(strings.drinkDialog.submit) }
                }
            )
            DrinkEditor(vm)
        }
    }
}

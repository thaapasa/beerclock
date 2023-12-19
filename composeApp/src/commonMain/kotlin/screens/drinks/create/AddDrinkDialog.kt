package fi.tuska.beerclock.screens.drinks.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.editor.DrinkEditor
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.components.FullScreenDialog
import fi.tuska.beerclock.ui.composables.rememberWithDispose

@Composable
fun AddDrinkDialog(
    proto: BasicDrinkInfo? = null,
    onDrinksUpdated: (() -> Unit)? = null,
    onClose: () -> Unit,
) {
    val vm = rememberWithDispose { NewDrinkViewModel(proto) }

    FullScreenDialog(onDismissRequest = onClose) {
        val strings = Strings.get()
        Column(modifier = Modifier.fillMaxWidth()) {
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
                        onClick = {
                            vm.addDrink {
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

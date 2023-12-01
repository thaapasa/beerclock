package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.components.FullScreenDialog
import fi.tuska.beerclock.ui.composables.rememberWithDispose

@Composable
fun AddDrinkDialog(onDrinksUpdated: (() -> Unit)? = null, onClose: () -> Unit) {
    val vm = rememberWithDispose { NewDrinkViewModel() }

    FullScreenDialog(onDismissRequest = onClose) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DialogHeader(
                titleText = strings.newDrink.title,
                leadingIcon = { modifier ->
                    AppIcon.CLOSE.iconButton(
                        onClick = onClose,
                        modifier = modifier,
                        contentDescription = strings.dialogClose
                    )
                },
                textButton = { modifier ->
                    TextButton(
                        enabled = !vm.saving,
                        onClick = {
                            vm.addDrink {
                                onDrinksUpdated?.invoke()
                                onClose()
                            }
                        },
                        modifier = modifier
                    ) { Text(strings.newDrink.submit) }
                }
            )
            DrinkEditor(vm)
        }
    }
}

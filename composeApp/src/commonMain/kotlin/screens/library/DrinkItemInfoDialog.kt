package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkInfoTable
import fi.tuska.beerclock.ui.components.DrinkDialog
import fi.tuska.beerclock.ui.components.DrinkNotes

@Composable
fun DrinkItemInfoDialog(
    drink: DrinkInfo,
    drinkDetails: DrinkDetails?,
    onClose: () -> Unit,
    onModify: ((drink: DrinkInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkInfo) -> Unit)? = null,
) {
    DrinkDialog(drink, onClose, buttonContent = {
        DrinkInfoDialogButtons(
            drink,
            onModify = { onModify?.invoke(it).also { onClose() } },
            onDelete = { onDelete?.invoke(it).also { onClose() } },
        )
    }) {
        DrinkInfoTable(drink, drinkDetails = drinkDetails)
        drink.note?.ifBlank { null }?.let {
            DrinkNotes { Text(it, style = MaterialTheme.typography.bodyMedium) }
        }
    }
}


@Composable
fun DrinkInfoDialogButtons(
    drink: DrinkInfo,
    onModify: ((drink: DrinkInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkInfo) -> Unit)? = null,
) {
    val strings = Strings.get()
    if (onModify == null && onDelete == null) {
        return
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        onDelete?.let {
            FilledTonalButton(
                { it(drink) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                colors = ButtonDefaults.filledTonalButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                AppIcon.DELETE.icon(modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(strings.dialogDelete)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        onModify?.let {
            FilledTonalButton(
                { it(drink) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                colors = ButtonDefaults.filledTonalButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                AppIcon.EDIT.icon(modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(strings.dialogEdit)
            }
        }
    }
}

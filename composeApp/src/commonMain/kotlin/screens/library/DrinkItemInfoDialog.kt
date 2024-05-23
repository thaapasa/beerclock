package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkNote
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkInfoTable
import fi.tuska.beerclock.ui.components.DrinkDialog
import fi.tuska.beerclock.ui.components.DrinkNotes
import fi.tuska.beerclock.ui.components.Rating

@Composable
fun DrinkItemInfoDialog(
    drink: DrinkInfo,
    drinkDetails: DrinkDetails?,
    notes: List<DrinkNote>,
    onClose: () -> Unit,
    onModify: ((drink: DrinkInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkInfo) -> Unit)? = null,
) {
    val strings = Strings.get()
    val times = DrinkTimeService()
    DrinkDialog(drink, onClose, buttonContent = {
        DrinkInfoDialogButtons(
            drink,
            onModify = { onModify?.invoke(it).also { onClose() } },
            onDelete = { onDelete?.invoke(it).also { onClose() } },
        )
    }) {
        DrinkInfoTable(drink, drinkDetails = drinkDetails)
        if (drink.note?.isNotBlank() == true || notes.isNotEmpty()) {
            DrinkNotes {
                var first = true
                if (drink.note?.isNotBlank() == true) {
                    Text(drink.note, style = MaterialTheme.typography.bodyMedium)
                    first = false
                }
                notes.map { note ->
                    if (!first) {
                        Text(
                            ". . .",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp).fillMaxWidth()
                                .alpha(0.6f),
                        )
                    }
                    note.rating?.let { Rating(it, modifier = Modifier.padding(bottom = 4.dp)) }
                    note.note?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                    Text(
                        strings.dateTime(times.toLocalDateTime(note.time)),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth().alpha(0.8f),
                        textAlign = TextAlign.Right,
                    )
                    first = false
                }
            }
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

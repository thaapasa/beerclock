package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkInfoTable
import fi.tuska.beerclock.ui.components.DrinkDialog

@Composable
fun DrinkInfoDialog(
    drink: DrinkRecordInfo,
    onClose: () -> Unit,
    onModify: ((drink: DrinkRecordInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
) {
    DrinkDialog(drink, onClose) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = drink.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        DrinkInfoTable(drink, time = drink.time)
        DrinkInfoDialogButtons(
            drink,
            onModify = { onModify?.invoke(it).also { onClose() } },
            onDelete = { onDelete?.invoke(it).also { onClose() } },
        )
    }
}


@Composable
fun DrinkInfoDialogButtons(
    drink: DrinkRecordInfo,
    onModify: ((drink: DrinkRecordInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
) {
    val strings = Strings.get()
    if (onModify == null && onDelete == null) {
        return
    }
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(24.dp))
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

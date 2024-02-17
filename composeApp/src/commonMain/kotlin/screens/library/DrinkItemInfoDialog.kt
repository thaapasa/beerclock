package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.images.largeImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkInfoTable

// Lifted higher to show on top of the drink list
val elevation = 24.dp

@Composable
fun DrinkItemInfoDialog(
    drink: DrinkInfo,
    drinkDetails: DrinkDetails?,
    onClose: () -> Unit,
    onModify: ((drink: DrinkInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkInfo) -> Unit)? = null,
) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Dialog(onDismissRequest = onClose) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = elevation,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation),
            contentColor = textColor
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) {
                    drink.image.largeImage(
                        modifier = Modifier.align(Alignment.Center).padding(top = 16.dp)
                    )
                    AppIcon.CLOSE.iconButton(
                        onClick = onClose,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                DrinkInfoTable(drink, drinkDetails = drinkDetails)

                DrinkInfoDialogButtons(
                    drink,
                    onModify = { onModify?.invoke(it).also { onClose() } },
                    onDelete = { onDelete?.invoke(it).also { onClose() } },
                )
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
    Spacer(modifier = Modifier.height(16.dp))
    Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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


@Composable
inline fun DrinkInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

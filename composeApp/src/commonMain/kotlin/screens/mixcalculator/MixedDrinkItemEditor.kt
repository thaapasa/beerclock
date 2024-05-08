package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrinkItem
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.composables.ViewModel

private val gap = 8.dp

@Composable
fun MixedDrinkItemEditor(vm: MixedDrinkItemEditorViewModel, onClose: () -> Unit) {
    val strings = Strings.get()
    DialogHeader(titleText = if (vm.isNewItem) strings.mixedDrinks.newItemTitle else strings.mixedDrinks.editItemTitle,
        height = 40.dp,
        textStyle = MaterialTheme.typography.titleMedium,
        trailingIcon = { modifier ->
            AppIcon.CLOSE.icon(
                modifier = modifier.clip(RoundedCornerShape(50))
                    .clickable(onClick = onClose)
            )
        })
    Row(modifier = Modifier.fillMaxWidth()) {
        DecimalField(
            label = { Text(strings.amountLabel) },
            value = vm.amount,
            onValueChange = { vm.amount = it },
            modifier = Modifier.width(96.dp),
            trailingIcon = { Text(strings.amountUnit) }
        )
        Spacer(Modifier.width(gap))
        OutlinedTextField(
            label = { Text(strings.drinkDialog.nameLabel) },
            value = vm.name,
            singleLine = true,
            onValueChange = { vm.name = it },
            modifier = Modifier.weight(1f),
        )
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        DecimalField(
            label = { Text(strings.drinkDialog.quantityLabel) },
            value = vm.quantityCl,
            onValueChange = { vm.quantityCl = it },
            modifier = Modifier.weight(1f),
            trailingIcon = { Text(strings.drinkDialog.quantityUnit) }
        )
        Spacer(modifier = Modifier.width(gap))
        DecimalField(
            label = { Text(strings.drinkDialog.abvLabel) },
            value = vm.abv,
            onValueChange = { vm.abv = it },
            modifier = Modifier.weight(1f),
            trailingIcon = { Text(strings.drinkDialog.abvUnit) }
        )
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        if (vm.canDelete) {
            Button(
                onClick = vm::delete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) { AppIcon.DELETE.icon() }
            Spacer(modifier = Modifier.width(gap))
        }
        Button(onClick = vm::save, enabled = vm.isValid()) { Text(strings.mixedDrinks.save) }
    }
}

class MixedDrinkItemEditorViewModel(
    proto: MixedDrinkItem? = null,
    private val saveAction: (item: MixedDrinkItem) -> Unit,
    private val deleteAction: (() -> Unit)? = null,
) : ViewModel() {
    var id = proto?.id
    var amount by mutableDoubleStateOf(proto?.amount ?: 1.0)
    var name by mutableStateOf(proto?.name ?: "")
    var abv by mutableDoubleStateOf(proto?.abvPercentage ?: 10.0)
    var quantityCl by mutableDoubleStateOf(proto?.quantityCl ?: 100.0)

    val isNewItem = proto == null
    fun isValid(): Boolean = name.isNotEmpty() && amount > 0 && abv >= 0.0 && quantityCl > 0

    fun save() {
        if (!isValid()) return
        saveAction(toMixedDrinkItem())
    }

    val canDelete = deleteAction != null

    fun delete() {
        deleteAction?.invoke()
    }

    fun toMixedDrinkItem(): MixedDrinkItem =
        MixedDrinkItem(
            id = id,
            amount = amount,
            name = name,
            abvPercentage = abv,
            quantityCl = quantityCl
        )

}

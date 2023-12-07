package fi.tuska.beerclock.screens.drinks.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel
import fi.tuska.beerclock.screens.history.UnitAvatar
import fi.tuska.beerclock.ui.components.DateInputField
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.TimeInputField

private val gap = 16.dp

@Composable
fun DrinkEditor(vm: DrinkEditorViewModel, modifier: Modifier = Modifier) {
    val strings = Strings.get()
    Column(modifier = modifier.padding(16.dp).fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DateInputField(
                value = vm.date,
                onValueChange = { vm.date = it },
                labelText = strings.newDrink.dateLabel,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(gap))
            TimeInputField(
                value = vm.time,
                onValueChange = { vm.time = it },
                labelText = strings.newDrink.timeLabel,
                modifier = Modifier.weight(1f),
            )
        }
        DrinkTimeSlider(
            value = vm.time,
            onValueChange = { vm.time = it },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            strings.newDrink.drinkTimeInfo(vm.localRealTime()),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, end = 8.dp),
        )

        Spacer(modifier = Modifier.height(gap))
        Row(Modifier.fillMaxWidth()) {
            TextField(
                label = { Text(strings.newDrink.nameLabel) },
                value = vm.name,
                onValueChange = { vm.name = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(gap))
            DrinkImageSelectField(
                value = vm.image,
                onValueChange = { vm.image = it },
                titleText = strings.newDrink.selectImageTitle
            )
        }
        Spacer(modifier = Modifier.height(gap))

        Row(modifier = Modifier.fillMaxWidth()) {
            DecimalField(
                label = { Text(strings.newDrink.abvLabel) },
                value = vm.abv,
                onValueChange = { vm.abv = it },
                modifier = Modifier.weight(1f),
                trailingIcon = { Text(strings.newDrink.abvUnit) }
            )
            Spacer(modifier = Modifier.width(gap))
            DecimalField(
                label = { Text(strings.newDrink.quantityLabel) },
                value = vm.quantityCl,
                onValueChange = { vm.quantityCl = it },
                modifier = Modifier.weight(1f),
                trailingIcon = { Text(strings.newDrink.quantityUnit) }
            )
            Spacer(modifier = Modifier.width(gap))
            UnitAvatar(
                units = vm.units(),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
        Slider(
            value = vm.quantityCl.toFloat(),
            onValueChange = { vm.quantityCl = it.toInt().toDouble() },
            valueRange = 1f..75f,
            steps = 74
        )
    }
}

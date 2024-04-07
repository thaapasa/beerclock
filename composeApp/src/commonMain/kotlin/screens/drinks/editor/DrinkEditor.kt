package fi.tuska.beerclock.screens.drinks.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel
import fi.tuska.beerclock.ui.components.DateInputField
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.RatingField
import fi.tuska.beerclock.ui.components.TimeInputField
import fi.tuska.beerclock.ui.components.UnitAvatar

private val gap = 16.dp

@Composable
fun DrinkEditor(vm: DrinkEditorViewModel, modifier: Modifier = Modifier, showTime: Boolean = true) {
    val strings = Strings.get()
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth().then(modifier)) {
        if (showTime) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DateInputField(
                    value = vm.date,
                    onValueChange = { vm.date = it },
                    labelText = strings.drinkDialog.dateLabel,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(gap))
                TimeInputField(
                    value = vm.time,
                    onValueChange = { vm.time = it },
                    labelText = strings.drinkDialog.timeLabel,
                    modifier = Modifier.weight(1f),
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AppIcon.CLOCK.icon(
                    strings.drinkDialog.timeLabel,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DrinkTimeSlider(
                    value = vm.time,
                    onValueChange = { vm.time = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                strings.drinkDialog.drinkTimeInfo(vm.localRealTime()),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, end = 8.dp),
            )

            Spacer(modifier = Modifier.height(gap))
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(strings.drinkDialog.nameLabel) },
                value = vm.name,
                singleLine = true,
                onValueChange = { vm.name = it },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(gap))
            DrinkImageSelectField(
                value = vm.image,
                onValueChange = { vm.image = it },
                titleText = strings.drinkDialog.selectImageTitle
            )
        }

        Spacer(modifier = Modifier.height(gap))
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(strings.drinkDialog.producerLabel) },
                value = vm.producer,
                singleLine = true,
                onValueChange = { vm.producer = it },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(gap))
        CategorySelector(vm.category, { vm.category = it })
        Spacer(modifier = Modifier.height(gap))

        Row(modifier = Modifier.fillMaxWidth()) {
            DecimalField(
                label = { Text(strings.drinkDialog.abvLabel) },
                value = vm.abv,
                onValueChange = { vm.abv = it },
                modifier = Modifier.weight(1f),
                leadingIcon = { AppIcon.BOLT.icon(modifier = Modifier.size(16.dp)) },
                trailingIcon = { Text(strings.drinkDialog.abvUnit) }
            )
            Spacer(modifier = Modifier.width(gap))
            DecimalField(
                label = { Text(strings.drinkDialog.quantityLabel) },
                value = vm.quantityCl,
                onValueChange = { vm.quantityCl = it },
                modifier = Modifier.weight(1f),
                leadingIcon = { AppIcon.GLASS_FULL.icon(modifier = Modifier.size(16.dp)) },
                trailingIcon = { Text(strings.drinkDialog.quantityUnit) }
            )
            Spacer(modifier = Modifier.width(gap))
            UnitAvatar(
                units = vm.units(),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppIcon.GLASS_FULL.icon(
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Slider(
                value = vm.quantityCl.toFloat(),
                onValueChange = { vm.quantityCl = it.toInt().toDouble() },
                valueRange = 1f..75f,
                steps = 74,
            )
        }
        Spacer(modifier = Modifier.height(gap))
        Row(modifier = Modifier.fillMaxWidth()) {
            RatingField(
                value = vm.rating,
                onValueChange = { vm.rating = it },
                label = { Text("Rating") })
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                label = { Text(strings.drinkDialog.noteLabel) },
                value = vm.note,
                onValueChange = { vm.note = it },
                modifier = Modifier.weight(1f),
                minLines = 3,
            )
        }
    }
}

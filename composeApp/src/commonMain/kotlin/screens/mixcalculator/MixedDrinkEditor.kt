package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.editor.DrinkImageSelectField
import fi.tuska.beerclock.ui.composables.ViewModel

val gap = 16.dp

@Composable
fun MixedDrinkEditor(vm: MixedDrinkEditorViewModel, onClose: () -> Unit) {
    val strings = Strings.get()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("Uusi juomasekoitus")
    }
    Spacer(modifier = Modifier.height(gap))
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
        Button(onClick = {}) { Text("Peruuta") }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}) { Text("Tallenna") }
    }
}

class MixedDrinkEditorViewModel(proto: MixedDrinkInfo) : ViewModel() {
    var name by mutableStateOf(proto.name)
    var image by mutableStateOf(proto.image)
}

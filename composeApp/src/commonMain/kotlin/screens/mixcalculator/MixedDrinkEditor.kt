package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.images.DrinkImagesList
import fi.tuska.beerclock.images.largeImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.ImagePreviewViewModel
import fi.tuska.beerclock.ui.components.ImageSelectDialog
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import kotlinx.coroutines.launch

val gap = 16.dp

@Composable
fun MixedDrinkEditor(vm: MixedDrinkEditorViewModel, onClose: () -> Unit) {
    val strings = Strings.get()
    var selectImage by remember { mutableStateOf(false) }
    val previewImg = rememberWithDispose { ImagePreviewViewModel() }

    if (selectImage) {
        ImageSelectDialog(
            onClose = { selectImage = false },
            options = DrinkImagesList,
            minImageSize = 64.dp,
            onValueChange = { vm.image = it },
            preview = previewImg,
        )
    }
    previewImg.Content()

    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        vm.image.largeImage(
            modifier = Modifier.align(Alignment.Center).padding(top = 16.dp)
                .clickable(onClick = { selectImage = true })
        )

        AppIcon.CLOSE.iconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopEnd)
        )
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
    }
    Spacer(modifier = Modifier.height(gap))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Button(onClick = { vm.save(andThen = onClose) }) { Text("Tallenna") }
    }
}

class MixedDrinkEditorViewModel(proto: MixedDrinkInfo) : ViewModel() {
    val id = proto.id
    var name by mutableStateOf(proto.name)
    var image by mutableStateOf(proto.image)
    var category by mutableStateOf(proto.category)
    val mixService = MixedDrinksService()

    fun toMixedDrinkInfo(): MixedDrinkInfo = MixedDrinkInfo(id = id, name, image, category)

    fun save(andThen: () -> Unit) {
        launch {
            if (id != null) {
                mixService.updateDrinkMix(id, toMixedDrinkInfo())
            } else {
                mixService.insertDrinkMix(toMixedDrinkInfo())
            }
            andThen()
        }
    }

}

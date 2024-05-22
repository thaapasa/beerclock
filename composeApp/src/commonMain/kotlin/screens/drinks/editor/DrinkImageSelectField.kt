package fi.tuska.beerclock.screens.drinks.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.images.DrinkImagesList
import fi.tuska.beerclock.ui.components.ImageSelectField

@Composable
fun DrinkImageSelectField(
    value: DrinkImage,
    onValueChange: (image: DrinkImage) -> Unit,
    titleText: String? = null,
) {
    ImageSelectField(
        value = value,
        onValueChange = onValueChange,
        options = DrinkImagesList,
        titleText = titleText,
        minImageSize = 64.dp
    )
}

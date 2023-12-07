package fi.tuska.beerclock.screens.drinks.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.ui.components.ImageSelectField

private val drinkOptions = DrinkImage.values().toList()

@Composable
fun DrinkImageSelectField(
    value: DrinkImage,
    onValueChange: (image: DrinkImage) -> Unit,
    titleText: String? = null
) {
    ImageSelectField(
        value = value,
        onValueChange = onValueChange,
        options = drinkOptions,
        titleText = titleText,
        valueToImage = { img: DrinkImage, modifier: Modifier -> img.smallImage(modifier = modifier) },
        minImageSize = 64.dp
    )
}

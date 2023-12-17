package fi.tuska.beerclock.screens.newdrink

import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.images.DrinkImage

class TextDrinkInfo(
    override val key: String,
    name: String,
    val icon: AppIcon,
    val onClick: () -> Unit,
) : BasicDrinkInfo(
    name = name,
    quantityCl = 0.0,
    abvPercentage = 0.0,
    image = DrinkImage.GENERIC_DRINK
)

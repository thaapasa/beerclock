package fi.tuska.beerclock.screens.newdrink

import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.DrinkImage

class TextDrinkInfo(override val key: String, name: String) : BasicDrinkInfo(
    name = name,
    quantityCl = 0.0,
    abvPercentage = 0.0,
    image = DrinkImage.GENERIC_DRINK
)

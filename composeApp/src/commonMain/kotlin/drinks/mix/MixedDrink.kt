package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.images.DrinkImage

data class MixedDrinkInfo(
    val name: String,
    val image: DrinkImage = DrinkImage.CAT_PUNCHES,
    val id: Long? = null,
)

package fi.tuska.beerclock.screens.mixcalculator

import fi.tuska.beerclock.drinks.Category

internal const val defaultMaxAbv = 25.0

internal val maxValuesByCategory = enumValues<Category>().associateWith {
    when (it) {
        Category.BEERS -> 8.0
        Category.WINES -> 18.0
        Category.COCKTAILS -> 20.0
        Category.SPIRITS -> 50.0
        Category.SPECIALITY -> 25.0
        Category.LOW_ALCOHOL -> 5.0
    }
}


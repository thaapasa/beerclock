package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.logging.getLogger

typealias DrinkAction = suspend (drink: DrinkDetailsFromEditor) -> DrinkRecordInfo

private val logger = getLogger("DrinkAction")

/** Default implementation for DrinkAction: Just drink it */
val drinkTheDrink: DrinkAction = { drink ->
    val inserted = DrinkService().insertDrink(drink)
    logger.info("Marking new drink: $drink with id ${inserted.id}")
    inserted
}

fun drinkAndThen(andThen: (drink: DrinkRecordInfo) -> Unit): DrinkAction {
    return { drink ->
        val newDrink = drinkTheDrink(drink)
        andThen(newDrink)
        newDrink
    }
}
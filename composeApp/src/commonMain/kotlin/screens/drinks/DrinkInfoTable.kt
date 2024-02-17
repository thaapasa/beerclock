package fi.tuska.beerclock.screens.drinks

import androidx.compose.runtime.Composable
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.history.DrinkInfoRow
import kotlinx.datetime.Instant

@Composable
fun DrinkInfoTable(
    drink: BasicDrinkInfo,
    time: Instant? = null,
    drinkDetails: DrinkDetails? = null
) {
    val strings = Strings.get()
    val times = DrinkTimeService()
    time?.let { DrinkInfoRow(strings.drink.timeInfoLabel, strings.drink.drinkTime(it)) }
    DrinkInfoRow(
        strings.drink.sizeInfoLabel,
        strings.drink.sizeInfo(drink.quantityCl, abvPercentage = drink.abvPercentage)
    )
    DrinkInfoRow(strings.drink.unitsInfoLabel, strings.drink.unitsInfo(drink.units()))
    DrinkInfoRow(
        strings.drink.alcoholGramsInfoLabel,
        strings.drink.alcoholAmountInfo(grams = drink.alcoholGrams, liters = drink.alcoholLiters)
    )
    DrinkInfoRow(
        strings.drink.burnOffTimeInfoLabel,
        strings.drink.burnOffTimeInfo(drink.burnOffTime())
    )
    drinkDetails?.let {
        DrinkInfoRow(
            strings.drink.totalTimesLabel,
            it.timesDrunk.toString()
        )
        if (it.timesDrunk > 0) {
            DrinkInfoRow(
                strings.drink.totalQuantityLabel,
                strings.drink.totalQuantity(it.quantityLiters)
            )
        }
        it.firstTimeDrunk?.let { time ->
            DrinkInfoRow(
                strings.drink.firstTimeLabel,
                strings.dateTime(times.toLocalDateTime(time))
            )
        }
        if (it.timesDrunk > 1) {
            it.lastTimeDrunk?.let { time ->
                DrinkInfoRow(
                    strings.drink.lastTimeLabel,
                    strings.dateTime(times.toLocalDateTime(time))
                )
            }
        }
    }
}

package fi.tuska.beerclock.screens.drinks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkDetails
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.localization.Strings
import kotlinx.datetime.Instant

@Composable
fun DrinkInfoTable(
    drink: BasicDrinkInfo,
    time: Instant? = null,
    drinkDetails: DrinkDetails? = null,
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


@Composable
inline fun DrinkInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}


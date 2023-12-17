package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.DrinkLibrary
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * An item in the drink library. These can be searched from new drink screen and used
 * to create new drink record entries.
 */
class DrinkInfo(record: DrinkLibrary) : KoinComponent, BasicDrinkInfo {
    private val prefs: GlobalUserPreferences by inject()
    override val name = record.name
    val id = record.id
    override val key = record.id
    val abv = record.abv
    val quantityLiters = record.quantity_liters
    override val quantityCl = record.quantity_liters * 100
    override val abvPercentage = record.abv * 100
    override val image = DrinkImage.forName(record.image)

    /** Amount of alcohol in the drink, in liters */
    val alcoholLiters: Double = record.quantity_liters * record.abv

    /** Amount of alcohol in the drink, in grams */
    val alcoholGrams: Double = alcoholLiters * BacFormulas.alcoholDensity

    /**
     * Given the selection of how many grams of alcohol are there in a single standard unit:
     * (from user preferences):
     * @return the number of units there are in this drink
     */
    override fun units() = BacFormulas.getUnitsFromAlcoholWeight(alcoholGrams, prefs.prefs)

    override fun toString() = "$name ($quantityCl cl $abvPercentage %)"
}

package fi.tuska.beerclock.screens.newdrink

import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TextDrinkInfo(override val key: String, override val name: String) : KoinComponent,
    BasicDrinkInfo {

    private val prefs: GlobalUserPreferences = get()

    override val quantityCl = 0.0
    override val abvPercentage = 0.0

    override val image = null

    /** Amount of alcohol in the drink, in liters */
    val alcoholLiters: Double = quantityCl * abvPercentage

    /** Amount of alcohol in the drink, in grams */
    val alcoholGrams: Double = alcoholLiters * BacFormulas.alcoholDensity

    /**
     * Given the selection of how many grams of alcohol are there in a single standard unit:
     * (from user preferences):
     * @return the number of units there are in this drink
     */
    override fun units() = BacFormulas.getUnitsFromAlcoholWeight(alcoholGrams, prefs.prefs)
}

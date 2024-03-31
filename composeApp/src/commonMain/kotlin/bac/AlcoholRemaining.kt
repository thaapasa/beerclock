package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.BacFormulas.burnOffAlcohol
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Utility class to calculate the amount of alcohol remaining in your body, in grams.
 */
class AlcoholRemaining(initialTime: Instant, initialAlcoholGrams: Double) : KoinComponent {
    constructor(drink: DrinkRecordInfo) : this(drink.time, drink.alcoholGrams)

    private val prefs: GlobalUserPreferences by inject()

    private var time: Instant = initialTime
    private var alcoholGrams: Double = initialAlcoholGrams

    fun get(): AlcoholAtTime = AlcoholAtTime(time, alcoholGrams)

    fun update(newTime: Instant, addAlcoholGrams: Double = 0.0) {
        val timePassed = newTime - time
        val burnRate = prefs.prefs.alcoholBurnOffRate
        alcoholGrams = burnOffAlcohol(alcoholGrams, timePassed, burnRate) + addAlcoholGrams
        time = newTime
    }

    fun update(drink: DrinkRecordInfo) = update(drink.time, drink.alcoholGrams)

    companion object {
        /**
         * @return how much alcohol is remaining at the given time from the given list of drinks
         */
        fun forDrinks(drinks: List<DrinkRecordInfo>, upToTime: Instant): AlcoholAtTime {
            if (drinks.isEmpty()) {
                return AlcoholAtTime(upToTime, 0.0)
            }
            val calc = AlcoholRemaining(drinks.first())
            drinks.drop(1).forEach(calc::update)
            calc.update(upToTime)
            return calc.get()
        }
    }
}

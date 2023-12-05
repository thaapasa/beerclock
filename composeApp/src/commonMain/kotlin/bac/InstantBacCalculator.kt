package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.BacFormulas.burnOffAlcohol
import fi.tuska.beerclock.bac.BacFormulas.timeToBurnAlcohol
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max
import kotlin.time.Duration.Companion.hours

/**
 * Utility class to ease calculating the alcohol left in your body, in grams.
 * Initialize with the time of the first drink, and the amount of alcohol in that drink;
 * and then call update for each subsequent drink (in ascending timestamp order) to
 * update the blood alcohol status for the next drink.
 */
class InstantBacCalculator(initialTime: Instant, initialAlcoholGrams: Double) : KoinComponent {
    private val prefs: GlobalUserPreferences by inject()

    private var events = mutableListOf(AlcoholAtTime(initialTime, initialAlcoholGrams))

    private var curTime: Instant = initialTime
    var curAlcoholGrams: Double = initialAlcoholGrams
        private set

    fun get(): AlcoholAtTime = AlcoholAtTime(curTime, curAlcoholGrams)
    fun list() = events.toList()

    fun update(newTime: Instant, addAlcoholGrams: Double = 0.0) {
        val burnOffRate = prefs.prefs.alcoholBurnOffRate
        val timePassed = newTime - curTime
        if (curAlcoholGrams > 0) {
            val timeToBurn = timeToBurnAlcohol(curAlcoholGrams, burnOffRate)
            if (timeToBurn < timePassed) {
                record(curTime + timeToBurn, 0.0)
            }
        }

        curAlcoholGrams = burnOffAlcohol(curAlcoholGrams, timePassed, burnOffRate)
        record(newTime, curAlcoholGrams)
        if (addAlcoholGrams > 0) {
            curAlcoholGrams += addAlcoholGrams
            record(newTime, curAlcoholGrams)
        }
        curTime = newTime
    }

    inline fun update(drink: DrinkRecordInfo) = update(drink.time, drink.alcoholGrams)

    private fun record(time: Instant, alcoholGrams: Double) {
        events.add(AlcoholAtTime(time, max(alcoholGrams, 0.0)))
    }

    companion object {
        fun calculate(
            startOfDay: AlcoholAtTime,
            drinks: List<DrinkRecordInfo>
        ): List<AlcoholAtTime> {
            val calc = InstantBacCalculator(startOfDay.time, startOfDay.alcoholGrams)
            drinks.forEach(calc::update)
            calc.update(startOfDay.time + 24.hours, 0.0)
            return calc.list()
        }
    }

}

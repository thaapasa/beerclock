package fi.tuska.beerclock.bac

import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
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
class AlcoholBurnCalculation(initialTime: Instant, initialAlcoholGrams: Double) : KoinComponent {
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
        val hoursPassed = timePassed.inHours()
        if (curAlcoholGrams > 0) {
            val hoursToBurnCurrent = curAlcoholGrams / burnOffRate
            if (hoursToBurnCurrent < hoursPassed) {
                record(curTime + hoursToBurnCurrent.hours, 0.0)
            }
        }

        val maxAlcoholGramsBurned =
            if (hoursPassed > 0.0) hoursPassed * burnOffRate
            else 0.0

        curAlcoholGrams = max(curAlcoholGrams - maxAlcoholGramsBurned, 0.0)
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
            val calc = AlcoholBurnCalculation(startOfDay.time, startOfDay.alcoholGrams)
            drinks.forEach(calc::update)
            calc.update(startOfDay.time + 24.hours, 0.0)
            return calc.list()
        }
    }

}

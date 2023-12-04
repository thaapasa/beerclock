package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.drinks.AlcoholCalculator.alcoholGramsBurnedPerHour
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max
import kotlin.time.Duration.Companion.hours

/**
 * Records what the blood alcohol level was at a given time, as grams of alcohol
 * left for your liver to burn.
 */
data class AlcoholAtTime(val time: Instant, val alcoholGrams: Double)

/**
 * Utility class to ease calculating the alcohol left in your body, in grams.
 * Initialize with the time of the first drink, and the amount of alcohol in that drink;
 * and then call update for each subsequent drink (in ascending timestamp order) to
 * update the blood alcohol status for the next drink.
 */
class AlcoholBurnCalculation(initialTime: Instant, initialAlcoholGrams: Double) : KoinComponent {
    constructor(drink: DrinkRecordInfo) : this(drink.time, drink.alcoholGrams)

    private var events = mutableListOf(AlcoholAtTime(initialTime, initialAlcoholGrams))

    private var time: Instant = initialTime
    var alcoholGrams: Double = initialAlcoholGrams
        private set

    private val prefs: GlobalUserPreferences by inject()

    fun get(): AlcoholAtTime = AlcoholAtTime(time, alcoholGrams)
    fun list() = events.toList()

    fun update(newTime: Instant, addAlcoholGrams: Double = 0.0) {
        val timePassed = newTime - time
        val hoursPassed = timePassed.inHours()
        if (alcoholGrams > 0) {
            val hoursToBurnCurrent = alcoholGrams / alcoholGramsBurnedPerHour(prefs.prefs)
            if (hoursToBurnCurrent < hoursPassed) {
                record(time + hoursToBurnCurrent.hours, 0.0)
            }
        }

        val alcoholGramsBurned =
            if (hoursPassed > 0.0) alcoholGramsBurnedPerHour(prefs.prefs) * hoursPassed
            else 0.0

        alcoholGrams = max(alcoholGrams - alcoholGramsBurned, 0.0)
        record(newTime, alcoholGrams)
        if (addAlcoholGrams > 0) {
            alcoholGrams += addAlcoholGrams
            record(newTime, alcoholGrams + addAlcoholGrams)
        }
        time = newTime
    }

    inline fun update(drink: DrinkRecordInfo) = update(drink.time, drink.alcoholGrams)

    private fun record(time: Instant, alcoholGrams: Double) {
        events.add(AlcoholAtTime(time, max(alcoholGrams, 0.0)))
    }

    companion object {
        fun forDrinks(drinks: List<DrinkRecordInfo>, upToTime: Instant): Double {
            if (drinks.isEmpty()) {
                return 0.0
            }
            val calc = AlcoholBurnCalculation(drinks.first())
            drinks.drop(1).forEach(calc::update)
            calc.update(upToTime)
            return calc.alcoholGrams
        }
    }
}

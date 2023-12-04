package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.AlcoholCalculator.alcoholGramsBurnedPerHour
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max

/**
 * Utility class to calculate the amount of alcohol remaining in your body, in grams.
 */
class AlcoholRemaining(initialTime: Instant, initialAlcoholGrams: Double) : KoinComponent {
    constructor(drink: DrinkRecordInfo) : this(drink.time, drink.alcoholGrams)

    private val prefs: GlobalUserPreferences by inject()

    private var time: Instant = initialTime
    var alcoholGrams: Double = initialAlcoholGrams
        private set

    fun get(): AlcoholAtTime = AlcoholAtTime(time, alcoholGrams)

    fun update(newTime: Instant, addAlcoholGrams: Double = 0.0) {
        val timePassed = newTime - time
        val hoursPassed = timePassed.inHours()

        val alcoholGramsBurned =
            if (hoursPassed > 0.0) alcoholGramsBurnedPerHour(prefs.prefs) * hoursPassed
            else 0.0

        alcoholGrams = max(alcoholGrams - alcoholGramsBurned, 0.0) + addAlcoholGrams
        time = newTime
    }

    inline fun update(drink: DrinkRecordInfo) = update(drink.time, drink.alcoholGrams)

    companion object {
        /**
         * @return how much alcohol is remaining at the given time from the given list of drinks
         */
        fun forDrinks(drinks: List<DrinkRecordInfo>, upToTime: Instant): Double {
            if (drinks.isEmpty()) {
                return 0.0
            }
            val calc = AlcoholRemaining(drinks.first())
            drinks.drop(1).forEach(calc::update)
            calc.update(upToTime)
            return calc.alcoholGrams
        }
    }
}

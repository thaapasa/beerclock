package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.BacFormulas.absorptionSchedule
import fi.tuska.beerclock.bac.BacFormulas.alcoholBurnOffRate
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.max
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

/**
 * This is a variant of InstantBacCalculator that tries to guess some estimate for how
 * alcohol is absorbed so that all alcohol in a drink is not instantly transferred to
 * your bloodstream.
 */
class AbsorbEstimatingBacCalculator(initialTime: Instant, initialAlcoholGrams: Double) :
    KoinComponent {
    private val prefs: GlobalUserPreferences by inject()

    private var events = mutableListOf(AlcoholAtTime(initialTime, initialAlcoholGrams))

    private var curTime: Instant = initialTime
    private var curAlcoholGrams: Double = initialAlcoholGrams
    private var absorptions = listOf<AbsorptionSchedule>()

    fun get(): AlcoholAtTime = AlcoholAtTime(curTime, curAlcoholGrams)
    fun list() = events.toList()

    /**
     * @return the current rate of how the alcohol content in the body changes,
     * expressed as grams/hour.
     */
    private fun currentAlcoholChangeRate(): Double {
        // in this context + means BAC is going up, so we need to negate the burnOffRate
        val baseRate = -alcoholBurnOffRate(prefs.prefs.volumeOfDistribution)
        return baseRate + absorptions.sumOf { it.gramsPerHour }
    }

    private fun nextChangeFromSchedules(): Instant? {
        return absorptions.minByOrNull { it.endTime }?.endTime
    }

    private fun advance(toTime: Instant) {
        val rate = currentAlcoholChangeRate()
        val duration = toTime - curTime
        curAlcoholGrams = max(curAlcoholGrams + rate * duration.inHours(), 0.0)
        curTime = toTime
        record(curTime, curAlcoholGrams)
        // Drop schedules that are past
        absorptions = absorptions.filter { it.endTime > toTime }
    }

    fun update(newTime: Instant, addAlcoholGrams: Double = 0.0) {
        val changeTime = nextChangeFromSchedules()
        if (changeTime != null && changeTime < newTime) {
            advance(changeTime)
            return update(newTime, addAlcoholGrams)
        }
        val curRate = currentAlcoholChangeRate()
        if (currentAlcoholChangeRate() < 0) {
            val timeToNew = newTime - curTime
            val burnOffDuration = (curAlcoholGrams / (-curRate)).hours
            if (burnOffDuration < timeToNew) {
                advance(curTime + burnOffDuration)
            }
        }

        advance(newTime)
        absorptions = absorptions + absorptionSchedule(addAlcoholGrams, newTime)
        curTime = newTime
    }

    fun update(drink: DrinkRecordInfo) = update(drink.time, drink.alcoholGrams)

    private fun record(time: Instant, alcoholGrams: Double) {
        events.add(AlcoholAtTime(time, max(alcoholGrams, 0.0)))
    }

    companion object {
        fun calculate(
            startOfDay: AlcoholAtTime,
            drinks: List<DrinkRecordInfo>,
        ): List<AlcoholAtTime> {
            val calc = AbsorbEstimatingBacCalculator(startOfDay.time, startOfDay.alcoholGrams)
            drinks.forEach(calc::update)
            calc.update(startOfDay.time + 24.hours, 0.0)
            return calc.list()
        }
    }

}

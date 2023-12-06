package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.interpolateFromList
import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.timeRange
import fi.tuska.beerclock.util.clamp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


object BacEventSmoother {
    fun smooth(
        events: List<AlcoholAtTime>,
        resolution: Duration = 10.minutes
    ): List<AlcoholAtTime> {
        val unsmoothed = generateUnsmoothed(events, resolution)
        return WeightedAverage.calculate(unsmoothed)
    }

    /**
     * @return an unsmoothed series of values evenly spaced with the given interval, from
     * the original alcohol level series
     */
    private fun generateUnsmoothed(
        events: List<AlcoholAtTime>,
        resolution: Duration = 10.minutes
    ) = timeRange(events).iterable(resolution)
        .map { interpolateFromList(events, it) }
}

internal object WeightedAverage {

    internal fun calculate(events: List<AlcoholAtTime>) =
        List(events.size) { i -> events.weightedValue(i) }

    private val weights = arrayOf(1, 3, 5, 8, 15, 8, 5, 3, 1)
    private val weightTotal = weights.sum()
    private val weightHalf = weights.size / 2
    private val wRange = (-weightHalf..weightHalf)

    private inline fun List<AlcoholAtTime>.weightedValue(pos: Int): AlcoholAtTime {
        val values =
            wRange.map { p ->
                get(
                    (p + pos).clamp(0, size - 1)
                ).alcoholGrams * weights[p + weightHalf] / weightTotal
            }
        return AlcoholAtTime(get(pos).time, values.sum())
    }
}

package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.interpolateFromList
import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.timeRange
import fi.tuska.beerclock.util.clampIndex
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


object BacEventSmoother {
    fun smooth(
        events: List<AlcoholAtTime>,
        resolution: Duration = 10.minutes,
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
        resolution: Duration = 10.minutes,
    ) = timeRange(events).iterable(resolution)
        .map { interpolateFromList(events, it) }
}

internal object WeightedAverage {

    internal fun calculate(events: List<AlcoholAtTime>) =
        List(events.size) { i -> events.weightedValue(i) }

    private const val smoothPoints = 6
    private const val weightBase = 2.1
    private val smoothWeights = List(smoothPoints + 1) { weightBase.pow(it) }
    private val weights = smoothWeights + smoothWeights.dropLast(1).reversed()
    private val wRange = (-smoothPoints..smoothPoints)
    private val totalWeights = weights.sum()

    private fun List<AlcoholAtTime>.weightedValue(pos: Int): AlcoholAtTime {
        val values =
            wRange.map {
                get(clampIndex(it + pos)).alcoholGrams * weights[it + smoothPoints]
            }
        return AlcoholAtTime(get(pos).time, values.sum() / totalWeights)
    }
}

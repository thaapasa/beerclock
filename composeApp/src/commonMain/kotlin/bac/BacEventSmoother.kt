package fi.tuska.beerclock.bac

import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.interpolateFromList
import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.timeRange
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


object BacEventSmoother {
    fun smooth(
        events: List<AlcoholAtTime>,
        resolution: Duration = 10.minutes
    ): List<AlcoholAtTime> {
        return generateUnsmoothed(events, resolution)
    }

    /**
     * @return an unsmoothed series of values evenly spaced with the given interval, from
     * the original alcohol level series
     */
    fun generateUnsmoothed(
        events: List<AlcoholAtTime>,
        resolution: Duration = 10.minutes
    ) = timeRange(events).iterable(resolution)
        .map { interpolateFromList(events, it) }
}

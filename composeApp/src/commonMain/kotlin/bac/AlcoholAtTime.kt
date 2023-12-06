package fi.tuska.beerclock.bac

import fi.tuska.beerclock.util.TimeInterval
import kotlinx.datetime.Instant

/**
 * Records what the blood alcohol level was at a given time, as grams of alcohol
 * left for your liver to burn.
 */
data class AlcoholAtTime(val time: Instant, val alcoholGrams: Double) {

    fun interpolate(other: AlcoholAtTime, atTime: Instant): AlcoholAtTime {
        if (time > other.time) {
            return other.interpolate(this, time)
        }

        val toEvent = (atTime - this.time)
        val range = (other.time - this.time)
        val offset = toEvent / range

        val rangeDelta = other.alcoholGrams - this.alcoholGrams

        return AlcoholAtTime(atTime, this.alcoholGrams + offset * rangeDelta)
    }

    companion object {
        inline fun interpolateFromList(list: List<AlcoholAtTime>, time: Instant): AlcoholAtTime {
            val futureIdx = list.indexOfFirst { it.time > time }
            if (futureIdx <= 0) return AlcoholAtTime(time, 0.0)
            return list[futureIdx - 1].interpolate(list[futureIdx], time)
        }

        inline fun timeRange(list: List<AlcoholAtTime>): TimeInterval {
            return TimeInterval(list.first().time, list.last().time)
        }
    }
}

package fi.tuska.beerclock.util

import kotlinx.datetime.Instant
import kotlin.time.Duration


/**
 * Closed-open range of time (start time is included, end is not).
 */
data class TimeInterval(val start: Instant, val end: Instant) {

    init {
        require(start <= end) { "Start of the time range must be before the end of the range." }
    }

    fun contains(instant: Instant): Boolean = instant in start..end && instant != end

    fun iterable(resolution: Duration): Iterable<Instant> {
        return Iterable { TimeIntervalIterator(start, end, resolution) }
    }
}


class TimeIntervalIterator(
    startTime: Instant,
    private val endTime: Instant,
    private val resolution: Duration
) : Iterator<Instant> {

    private var currentTime = startTime

    override fun hasNext(): Boolean = currentTime < endTime

    override fun next(): Instant {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        val nextTime = currentTime
        currentTime = currentTime.plus(resolution)
        return if (nextTime < endTime) nextTime else endTime
    }
}

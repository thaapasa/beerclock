package fi.tuska.beerclock.database

import kotlinx.datetime.Instant

// Extension function for Instant to convert to Unix timestamps used in the database
fun Instant.toDbTime(): Long = this.epochSeconds

// Extension function for Long values from DB to convert to Instant
fun Instant.Companion.fromDbTime(stamp: Long): Instant = Instant.fromEpochSeconds(stamp)

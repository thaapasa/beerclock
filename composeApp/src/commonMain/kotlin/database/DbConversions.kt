package fi.tuska.beerclock.database

import kotlinx.datetime.Instant

// Extension function for Instant to convert to Unix Timestamp
fun Instant.toUnixTimestamp(): Long = this.epochSeconds

// Extension function for Int to convert to Instant
fun Long.toInstant(): Instant = Instant.fromEpochSeconds(this.toLong())

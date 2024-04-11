package fi.tuska.beerclock.wear

import kotlinx.datetime.Instant

expect suspend fun sendCurrentBacStatusToWatch(state: CurrentBacStatus)

data class CurrentBacStatus(
    val time: Instant, val dailyUnits: Double,
    val alcoholGrams: Double,
    val volumeOfDistribution: Double,
)

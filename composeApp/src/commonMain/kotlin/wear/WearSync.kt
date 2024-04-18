package fi.tuska.beerclock.wear

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Instant

expect suspend fun sendCurrentBacStatusToWatch(state: CurrentBacStatus)

data class CurrentBacStatus(
    val locale: Locale?,
    val time: Instant,
    val dailyUnits: Double,
    val maxDailyUnits: Double,
    val alcoholGrams: Double,
    val volumeOfDistribution: Double,
    val maxBac: Double,
)

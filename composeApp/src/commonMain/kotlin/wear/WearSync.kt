package fi.tuska.beerclock.wear

import androidx.compose.ui.text.intl.Locale
import kotlin.time.Instant

expect fun isWatchSupported(): Boolean

expect suspend fun sendCurrentBacStatusToWatch(status: CurrentBacStatus)

data class CurrentBacStatus(
    val locale: Locale?,
    val time: Instant,
    val dailyUnits: Double,
    val dayEndTime: Instant,
    val maxDailyUnits: Double,
    val alcoholGrams: Double,
    val volumeOfDistribution: Double,
    val maxBac: Double,
)

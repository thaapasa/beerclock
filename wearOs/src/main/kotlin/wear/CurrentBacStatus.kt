package fi.tuska.beerclock.wear

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.Locale
import kotlin.math.min

private const val millisInHour = 3_600_000.0

@Parcelize
data class CurrentBacStatus(
    val languageTag: String?,
    val time: Instant,
    val dailyUnits: Double,
    val maxDailyUnits: Double,
    val dayEndTime: Instant,
    val alcoholGrams: Double,
    val volumeOfDistribution: Double,
    val maxBac: Double,
) : Parcelable {

    @IgnoredOnParcel
    val locale = languageTag?.let { Locale(it) }

    /**
     * Blood alcohol concentration (per mille).
     * See BacFormulas.kt.
     */
    @IgnoredOnParcel
    val bloodAlcoholConcentration = alcoholGrams / volumeOfDistribution

    /**
     * Alcohol burn-off rate, in grams per hour.
     * See BacFormulas.kt
     */
    @IgnoredOnParcel
    val alcoholBurnOffRateGpH = volumeOfDistribution * 0.15

    /**
     * Calculates remaining alcohol at given time, calculated from the initial amount recorded
     * by this status.
     *
     * @return BAC (per mille).
     */
    fun bacAtTime(targetTime: Instant): Double {
        val durationMillis = min(targetTime.toEpochMilli() - time.toEpochMilli(), 0)
        val burnedOffAlcoholGrams = alcoholBurnOffRateGpH * durationMillis / millisInHour
        val remainingAlcoholGrams = alcoholGrams - min(burnedOffAlcoholGrams, alcoholGrams)
        return remainingAlcoholGrams / volumeOfDistribution
    }

    fun dailyUnitsAtTime(targetTime: Instant): Double = when {
        targetTime < dayEndTime -> dailyUnits
        else -> 0.0
    }

    companion object
}

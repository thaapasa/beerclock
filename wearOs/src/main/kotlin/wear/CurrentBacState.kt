package fi.tuska.beerclock.wear

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class CurrentBacStatus(
    val time: Instant,
    val dailyUnits: Double,
    val alcoholGrams: Double,
    val volumeOfDistribution: Double,
) : Parcelable {
    companion object
}

package fi.tuska.beerclock.wear

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.tasks.await
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.cancellation.CancellationException

private val logger = getLogger("WearSync")

actual fun isWatchSupported(): Boolean = true

actual suspend fun sendCurrentBacStatusToWatch(status: CurrentBacStatus) {
    val context: Context by inject(Context::class.java)
    val dataClient = Wearable.getDataClient(context)

    try {
        val request = PutDataMapRequest.create("/current-bac").apply {
            dataMap.putString("locale", status.locale?.toLanguageTag() ?: "")
            dataMap.putLong("time", status.time.toEpochMilliseconds())
            dataMap.putDouble("dailyUnits", status.dailyUnits)
            dataMap.putDouble("maxDailyUnits", status.maxDailyUnits)
            dataMap.putLong("dayEndTime", status.dayEndTime.toEpochMilliseconds())
            dataMap.putDouble("alcoholGrams", status.alcoholGrams)
            dataMap.putDouble("volumeOfDistribution", status.volumeOfDistribution)
            dataMap.putDouble("maxBac", status.maxBac)
        }
            .asPutDataRequest()
            .setUrgent()

        val result = dataClient.putDataItem(request).await()

        logger.info("Saved $status to data item $request -> $result")
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Exception) {
        logger.warn("Saving data failed: $e")
    }
}

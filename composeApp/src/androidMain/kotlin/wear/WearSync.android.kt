package fi.tuska.beerclock.wear

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.tasks.await
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.cancellation.CancellationException

private val logger = getLogger("WearSync")

actual suspend fun sendCurrentBacStatusToWatch(state: CurrentBacStatus) {
    val context: Context by inject(Context::class.java)
    val dataClient = Wearable.getDataClient(context)

    try {
        val request = PutDataMapRequest.create("/current-bac").apply {
            dataMap.putString("locale", state.locale?.toLanguageTag() ?: "")
            dataMap.putLong("time", state.time.toEpochMilliseconds())
            dataMap.putDouble("dailyUnits", state.dailyUnits)
            dataMap.putDouble("maxDailyUnits", state.maxDailyUnits)
            dataMap.putDouble("alcoholGrams", state.alcoholGrams)
            dataMap.putDouble("volumeOfDistribution", state.volumeOfDistribution)
            dataMap.putDouble("maxBac", state.maxBac)
        }
            .asPutDataRequest()
            .setUrgent()

        val result = dataClient.putDataItem(request).await()

        logger.info("Saved $state to data item $request -> $result")
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Exception) {
        logger.warn("Saving data failed: $e")
    }
}

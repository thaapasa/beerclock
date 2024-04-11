package fi.tuska.beerclock.wear.datalayer

import android.util.Log
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import fi.tuska.beerclock.defaultTime
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.saveState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant

class DataListenerService : WearableListenerService() {

    private val messageClient by lazy { Wearable.getMessageClient(this) }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onDataChanged(events: DataEventBuffer) {
        super.onDataChanged(events)
        try {
            events.forEach { event ->
                val map = DataMapItem.fromDataItem(event.dataItem).dataMap
                val state = CurrentBacStatus(
                    time = Instant.ofEpochMilli(map.getLong("time", defaultTime)),
                    dailyUnits = map.getDouble("dailyUnits"),
                    alcoholGrams = map.getDouble("alcoholGrams"),
                    volumeOfDistribution = map.getDouble("volumeOfDistribution"),
                )
                Log.i(TAG, "Data event -> $state")

                scope.launch {
                    state.saveState(context = applicationContext)
                    Log.i(TAG, "Wrote state to prefs")
                }
            }
        } finally {
            // Always close events
            events.close()
        }
        /*
                    scope.launch {
                        try {
                            val uri = event.dataItem.uri
                            val nodeId = uri.host!!
                            val payload = event.dataItem.data
                            messageClient.sendMessage(
                                nodeId,
                                DATA_ITEM_RECEIVED_PATH,
                                payload
                            )
                                .await()
                            Log.d(TAG, "Message sent successfully")
                        } catch (cancellationException: CancellationException) {
                            throw cancellationException
                        } catch (exception: Exception) {
                            Log.w(TAG, "Message failed: $exception")
                        }
                    }

         */

    }

    override fun onMessageReceived(event: MessageEvent) {
        super.onMessageReceived(event)
        Log.i(TAG, "Received message!")
    }


    companion object {
        private const val TAG = "BeerDataListener"
        private const val DATA_ITEM_RECEIVED_PATH = "/data-item-received"
    }
}

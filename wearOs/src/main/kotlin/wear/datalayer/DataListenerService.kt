package fi.tuska.beerclock.wear.datalayer

import android.util.Log
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import fi.tuska.beerclock.defaultTime
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.complication.CurrentBacComplicationService
import fi.tuska.beerclock.wear.complication.DailyUnitsComplicationService
import fi.tuska.beerclock.wear.saveState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Locale

class DataListenerService : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)
        dataEvents.use { events ->
            events.forEach { event ->
                val map = DataMapItem.fromDataItem(event.dataItem).dataMap
                val languageTag = map.getString("locale")
                val state = CurrentBacStatus(
                    languageTag = languageTag?.ifBlank { null },
                    time = Instant.ofEpochMilli(map.getLong("time", defaultTime)),
                    dailyUnits = map.getDouble("dailyUnits"),
                    dayEndTime = Instant.ofEpochMilli(map.getLong("dayEndTime", defaultTime)),
                    maxDailyUnits = map.getDouble("maxDailyUnits"),
                    alcoholGrams = map.getDouble("alcoholGrams"),
                    volumeOfDistribution = map.getDouble("volumeOfDistribution"),
                    maxBac = map.getDouble("maxBac"),
                )
                Log.i(TAG, "Data event -> $state")

                state.locale?.let { Locale.setDefault(it) }

                scope.launch {
                    state.saveState(context = applicationContext)
                    Log.i(TAG, "Wrote state to prefs")

                    CurrentBacComplicationService.requestUpdate(applicationContext)
                    DailyUnitsComplicationService.requestUpdate(applicationContext)
                    Log.i(TAG, "Requested complication updates")
                }
            }
        }
    }

    companion object {
        private const val TAG = "BeerDataListener"
    }
}

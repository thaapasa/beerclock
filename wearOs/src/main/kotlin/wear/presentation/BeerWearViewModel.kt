package fi.tuska.beerclock.wear.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.flowCurrentState
import fi.tuska.beerclock.wear.presentation.components.GaugeValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.time.Duration.Companion.seconds

val pauseBetweenUpdates = 30.seconds

class BeerWearViewModel(application: Application) :
    AndroidViewModel(application) {

    val bacGauge =
        GaugeValue(initialValue = 0.0, maxValue = 1.4, iconRes = R.drawable.ic_permille)

    val dailyUnitsGauge =
        GaugeValue(initialValue = 0.0, maxValue = 7.0, iconRes = R.drawable.ic_local_bar)

    private var lastStatus: CurrentBacStatus? = null

    init {
        Log.i(TAG, "Initializing BeerWear VM")
        viewModelScope.launch {
            CurrentBacStatus.flowCurrentState(getApplication<Application>().applicationContext)
                .collect {
                    Log.i(TAG, "Got new BAC status: $it")
                    lastStatus = it
                    updateBacStatus()
                }
        }
        updateBacContinuously()
    }

    private fun updateBacStatus() {
        lastStatus?.let {
            val now = Instant.now()
            bacGauge.setValue(value = it.bacAtTime(now), maxValue = it.maxBac)
            dailyUnitsGauge.setValue(value = it.dailyUnitsAtTime(now), maxValue = it.maxDailyUnits)
        } ?: {
            bacGauge.setValue(value = 0.0, maxValue = 1.5)
            dailyUnitsGauge.setValue(value = 0.0, maxValue = 7.0)
        }
    }


    private fun updateBacContinuously() {
        viewModelScope.launch {
            while (isActive) {
                delay(timeMillis = pauseBetweenUpdates.inWholeMilliseconds)
                if (isActive) {
                    updateBacStatus()
                }
            }
        }
    }

    companion object {
        private const val TAG = "BeerWearViewModel"
    }

}

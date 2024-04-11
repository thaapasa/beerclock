package fi.tuska.beerclock.wear.complication

import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.GoalProgressComplicationData
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.RangedValueComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.getState
import kotlin.math.max
import kotlin.math.min

private val TAG = "AbvComplications"

/**
 * Service that returns the current blood alcohol concentration value (per mille)
 * based on the data recorded to BeerClock app.
 */
class CurrentBacComplicationService : SuspendingComplicationDataSourceService() {

    override fun onComplicationActivated(complicationInstanceId: Int, type: ComplicationType) {
        super.onComplicationActivated(complicationInstanceId, type)
        Log.i(TAG, "Activated complication")
    }

    override fun onComplicationDeactivated(complicationInstanceId: Int) {
        Log.i(TAG, "Stopping data event listening")
        //Wearable.getDataClient(this).removeListener(this)
        super.onComplicationDeactivated(complicationInstanceId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Created ABV service")
    }

    /*
        override fun onDataChanged(dataEvents: DataEventBuffer) {
            Log.i(TAG, "Got data event!!")
            dataEvents.forEach { event ->
                if (event.type == DataEvent.TYPE_DELETED) {
                    Log.d(TAG, "DataItem deleted: " + event.dataItem.uri)
                } else if (event.type == DataEvent.TYPE_CHANGED) {
                    Log.d(TAG, "DataItem changed: " + event.dataItem.uri)
                }
            }
        }
    *7

     */
    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && type == ComplicationType.GOAL_PROGRESS -> createGoalProgressData(
                0.7f
            )

            type == ComplicationType.RANGED_VALUE -> createRangedData(0.7f)
            else -> null
        }
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData? {
        val state = CurrentBacStatus.getState(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && request.complicationType == ComplicationType.GOAL_PROGRESS) {
            return createGoalProgressData(state.dailyUnits.toFloat())
        }
        return createRangedData(state.dailyUnits.toFloat())
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createGoalProgressData(value: Float) = GoalProgressComplicationData.Builder(
        value = value, targetValue = 1.5f,
        contentDescription = PlainComplicationText.Builder("Current ABV").build(),
    )
        .setTitle(PlainComplicationText.Builder(value.toString()).build())
        .setMonochromaticImage(getMonochromaticImage())
        .build()

    private fun createRangedData(value: Float) = RangedValueComplicationData.Builder(
        value = value, max = max(value, 1.5f), min = min(0.0f, value),
        contentDescription = PlainComplicationText.Builder("Current ABV").build(),
    )
        .setTitle(PlainComplicationText.Builder(value.toString()).build())
        .setMonochromaticImage(getMonochromaticImage())
        .build()

    private fun getMonochromaticImage() = MonochromaticImage.Builder(
        Icon.createWithResource(applicationContext, R.drawable.ic_permille)
    ).build()
}

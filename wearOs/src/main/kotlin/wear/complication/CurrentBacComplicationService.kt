package fi.tuska.beerclock.wear.complication

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.wear.protolayout.expression.DynamicBuilders
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.GoalProgressComplicationData
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.RangedValueComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.getState
import java.time.Instant
import kotlin.math.max
import kotlin.math.min

private const val TAG = "BacComplication"

/**
 * Service that returns the current blood alcohol concentration value (per mille)
 * based on the data recorded to BeerClock app.
 */
class CurrentBacComplicationService : SuspendingComplicationDataSourceService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && type == ComplicationType.GOAL_PROGRESS -> createGoalProgressData(
                0.7f, 1.5f
            )

            type == ComplicationType.RANGED_VALUE -> createRangedData(0.7f, 1.5f)
            else -> null
        }
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData? {
        val state = CurrentBacStatus.getState(applicationContext)

        val bacValue = state.bacAtTime(Instant.now()).toFloat()
        Log.i(TAG, "Requested BAC: $bacValue from ${state.bloodAlcoholConcentration} <- $state")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && request.complicationType == ComplicationType.GOAL_PROGRESS) {
            return createGoalProgressData(bacValue, 1.5f)
        }
        return createRangedData(bacValue, 1.5f)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createGoalProgressData(value: Float, maxValue: Float) =
        GoalProgressComplicationData.Builder(
            fallbackValue = value,
            targetValue = maxValue,
            dynamicValue = DynamicBuilders.DynamicFloat.animate(0f, value),
            contentDescription = createContentDescription(),
        )
            .setTitle(createTitle(value))
            .setMonochromaticImage(createMonochromaticImage())
            .build()

    private fun createRangedData(value: Float, maxValue: Float) =
        RangedValueComplicationData.Builder(
            value = value,
            max = max(value, maxValue),
            min = min(0f, value),
            contentDescription = createContentDescription(),
        )
            .setTitle(createTitle(value))
            .setMonochromaticImage(createMonochromaticImage())
            .build()

    private fun createTitle(value: Float) =
        PlainComplicationText.Builder(getString(R.string.bac_complication_value, value)).build()

    private fun createMonochromaticImage() = MonochromaticImage.Builder(
        Icon.createWithResource(applicationContext, R.drawable.ic_permille)
    ).build()

    private fun createContentDescription() =
        PlainComplicationText.Builder(getString(R.string.bac_complication_label))
            .build()

    companion object {
        fun componentName(context: Context): ComponentName =
            ComponentName(context, this::class.java.declaringClass!!)

        fun requestUpdate(context: Context) {
            ComplicationDataSourceUpdateRequester
                .create(
                    context = context,
                    complicationDataSourceComponent = componentName(context),
                )
                .requestUpdateAll()
        }
    }

}

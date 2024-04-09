package fi.tuska.beerclock.wear.complication

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.GoalProgressComplicationData
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.RangedValueComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import fi.tuska.beerclock.wear.R

/**
 * Service that returns the current alcohol blood concentration value (per mille)
 * based on the data recorded to BeerClock app.
 */
class CurrentAbvComplicationService : SuspendingComplicationDataSourceService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && type == ComplicationType.GOAL_PROGRESS -> createGoalProgressData(
                0.7f
            )

            type == ComplicationType.RANGED_VALUE -> createRangedData(0.7f)
            else -> null
        }
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && request.complicationType == ComplicationType.GOAL_PROGRESS) {
            return createGoalProgressData(0.7f)
        }
        return createRangedData(0.7f)
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
        value = value, max = 1.5f, min = 0.0f,
        contentDescription = PlainComplicationText.Builder("Current ABV").build(),
    )
        .setTitle(PlainComplicationText.Builder(value.toString()).build())
        .setMonochromaticImage(getMonochromaticImage())
        .build()

    private fun getMonochromaticImage() = MonochromaticImage.Builder(
        Icon.createWithResource(applicationContext, R.drawable.ic_permille)
    ).build()
}

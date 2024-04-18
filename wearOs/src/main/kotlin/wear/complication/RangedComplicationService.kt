package fi.tuska.beerclock.wear.complication

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.GoalProgressComplicationData
import androidx.wear.watchface.complications.data.MonochromaticImage
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.RangedValueComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import fi.tuska.beerclock.wear.CurrentBacStatus
import fi.tuska.beerclock.wear.LocaleHelper
import fi.tuska.beerclock.wear.getState
import kotlin.math.max
import kotlin.math.min

abstract class RangedComplicationService(
    @DrawableRes private val iconRes: Int,
    @StringRes private val valueRes: Int,
    @StringRes private val labelRes: Int,
) :
    SuspendingComplicationDataSourceService() {

    abstract fun toComplicationData(state: CurrentBacStatus): RangedData
    abstract fun previewData(): RangedData

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        val data = previewData()
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && type == ComplicationType.GOAL_PROGRESS -> createGoalProgressData(
                data
            )

            type == ComplicationType.RANGED_VALUE -> createRangedData(data)
            else -> null
        }
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData? {
        val state = CurrentBacStatus.getState(applicationContext)

        state.locale?.let {
            LocaleHelper.setCurrentLocale(applicationContext, it)
        }
        val data = toComplicationData(state)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && request.complicationType == ComplicationType.GOAL_PROGRESS) {
            return createGoalProgressData(data)
        }
        return createRangedData(data)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createGoalProgressData(data: RangedData) =
        GoalProgressComplicationData.Builder(
            value = data.value,
            targetValue = data.max,
            contentDescription = createContentDescription(),
        )
            .setTitle(createTitle(data))
            .setMonochromaticImage(createMonochromaticImage())
            .build()

    private fun createRangedData(data: RangedData) =
        RangedValueComplicationData.Builder(
            value = data.value,
            max = max(data.value, data.max),
            min = min(0f, data.value),
            contentDescription = createContentDescription(),
        )
            .setTitle(createTitle(data))
            .setMonochromaticImage(createMonochromaticImage())
            .build()

    private fun createTitle(data: RangedData) =
        PlainComplicationText.Builder(getString(valueRes, data.value)).build()

    private fun createMonochromaticImage() = MonochromaticImage.Builder(
        Icon.createWithResource(applicationContext, iconRes)
    ).build()

    private fun createContentDescription() =
        PlainComplicationText.Builder(getString(labelRes)).build()

}

data class RangedData(val value: Float, val max: Float)

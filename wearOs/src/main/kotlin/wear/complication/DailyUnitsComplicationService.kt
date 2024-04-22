package fi.tuska.beerclock.wear.complication

import android.content.ComponentName
import android.content.Context
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.CurrentBacStatus
import java.time.Instant

/**
 * Service that returns the current number of units consumed today (standard units in user's country)
 * based on the data recorded to BeerClock app.
 */
class DailyUnitsComplicationService :
    RangedComplicationService(
        iconRes = R.drawable.ic_local_bar,
        valueRes = R.string.daily_units_complication_value,
        labelRes = R.string.daily_units_complication_label,
    ) {

    override fun previewData(): RangedData = RangedData(3.7f, 7.0f)

    override fun toComplicationData(state: CurrentBacStatus, currentTime: Instant): RangedData =
        RangedData(
            value = state.dailyUnitsAtTime(currentTime).toFloat(),
            max = state.maxDailyUnits.toFloat()
        )

    companion object {
        private fun componentName(context: Context): ComponentName =
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

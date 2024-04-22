package fi.tuska.beerclock.wear.complication

import android.content.ComponentName
import android.content.Context
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.CurrentBacStatus
import java.time.Instant

/**
 * Service that returns the current blood alcohol concentration value (per mille)
 * based on the data recorded to BeerClock app.
 */
class CurrentBacComplicationService :
    RangedComplicationService(
        iconRes = R.drawable.ic_permille,
        valueRes = R.string.bac_complication_value,
        labelRes = R.string.bac_complication_label,
    ) {

    override fun previewData(): RangedData = RangedData(0.7f, 1.0f)

    override fun toComplicationData(state: CurrentBacStatus, currentTime: Instant): RangedData =
        RangedData(value = state.bacAtTime(currentTime).toFloat(), max = state.maxBac.toFloat())

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

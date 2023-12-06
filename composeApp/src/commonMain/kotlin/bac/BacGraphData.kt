package fi.tuska.beerclock.bac

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.interpolateFromList
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.graphs.AreaChart
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import io.github.koalaplot.core.line.Point
import io.github.koalaplot.core.xychart.XYChartScope
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.max
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class BacGraphData(private val events: List<AlcoholAtTime>) :
    KoinComponent {

    private val prefs: GlobalUserPreferences = get()
    private val times = DrinkTimeService()
    private val strings = Strings.get()
    private val dayStart =
        times.dayStartTime(times.toLocalDate(events.firstOrNull()?.time ?: Clock.System.now()))

    private fun dailyHourLabel(hour: Float) =
        times.toLocalDateTime(dayStart + hour.toDouble().hours)

    private inline fun atTime(time: Instant): AlcoholAtTime = interpolateFromList(events, time)

    /**
     * The maximum amount of alcohol on the system during the entire day.
     */
    private val maxAlcoholConcentration = events.maxByOrNull { it.alcoholGrams }
        ?.let { BacFormulas.bloodAlcoholConcentration(it.alcoholGrams, prefs.prefs) } ?: 0.0

    fun graphDef() = GraphDefinition(
        xRange = 0f..24f,
        yRange = 0f..max(1.0, maxAlcoholConcentration + 0.1).toFloat(),
        xTitle = strings.home.bacTime,
        yTitle = strings.home.bacPermilles,
        formatXLabel = { strings.time(dailyHourLabel(it)) + " " }
    )

    fun pastEvents(now: Instant): List<Point<Float, Float>> =
        (events.filter { it.time <= now } + atTime(now)).map { it.toGraphPoint() }

    fun futureEvents(now: Instant): List<Point<Float, Float>> =
        (listOf(atTime(now)) + events.filter { it.time > now }).map { it.toGraphPoint() }


    private inline fun Instant.toDailyHours(): Float = (this - dayStart).inHours().toFloat()

    private inline fun AlcoholAtTime.toGraphPoint(): Point<Float, Float> = Point(
        time.toDailyHours(),
        BacFormulas.bloodAlcoholConcentration(alcoholGrams, prefs.prefs).toFloat()
    )

    @Composable
    fun drawAreas(
        scope: XYChartScope<Float, Float>,
        now: Instant = Clock.System.now(),
        color: Color = MaterialTheme.colorScheme.primary
    ) {
        scope.AreaChart(pastEvents(now), color = color, alpha = 0.8f)
        scope.AreaChart(futureEvents(now), color = color, alpha = 0.3f)
    }

    companion object {
        fun smoothed(events: List<AlcoholAtTime>, resolution: Duration = 10.minutes): BacGraphData {
            return BacGraphData(BacEventSmoother.smooth(events, resolution))
        }
    }
}

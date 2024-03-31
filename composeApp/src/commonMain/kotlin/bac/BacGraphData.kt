package fi.tuska.beerclock.bac

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.AlcoholAtTime.Companion.interpolateFromList
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.graphs.AreaPlot
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import io.github.koalaplot.core.xygraph.LinearAxisModel
import io.github.koalaplot.core.xygraph.Point
import io.github.koalaplot.core.xygraph.XYGraphScope
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
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
        times.dayStartTime(times.currentDrinkDay(events.firstOrNull()?.time ?: times.now()))

    private fun dailyHourLabel(hour: Float) =
        times.toLocalDateTime(dayStart + hour.toDouble().hours)

    private fun atTime(time: Instant): AlcoholAtTime = interpolateFromList(events, time)

    /**
     * The maximum amount of alcohol on the system during the entire day.
     */
    private val maxAlcoholConcentration = events.maxByOrNull { it.alcoholGrams }
        ?.let { BacFormulas.bloodAlcoholConcentration(it.alcoholGrams, prefs.prefs) } ?: 0.0

    val maxY = max(0.7, maxAlcoholConcentration + 0.1).toFloat()

    fun graphDef() = GraphDefinition(
        xAxisModel = LinearAxisModel(
            range = 0f..24f,
            minorTickCount = 1,
            minimumMajorTickIncrement = 2f,
            minimumMajorTickSpacing = 10.dp,
        ),
        yAxisModel = LinearAxisModel(range = 0f..maxY),
        xTitle = strings.home.bacTime,
        yTitle = strings.home.bacPermilles,
        formatXLabel = { strings.time(dailyHourLabel(it)) + " " }
    )

    private fun pastEvents(now: Instant): List<Point<Float, Float>> =
        (events.filter { it.time <= now } + atTime(now)).map { it.toGraphPoint() }

    private fun futureEvents(now: Instant): List<Point<Float, Float>> =
        (listOf(atTime(now)) + events.filter { it.time > now }).map { it.toGraphPoint() }


    private fun Instant.toDailyHours(): Float = (this - dayStart).inHours().toFloat()

    private fun AlcoholAtTime.toGraphPoint(): Point<Float, Float> = Point(
        time.toDailyHours(),
        BacFormulas.bloodAlcoholConcentration(alcoholGrams, prefs.prefs).toFloat()
    )

    fun toGraphX(time: LocalTime): Float {
        val local = times.toLocalDateTime(dayStart)
        val instant = times.toInstant(LocalDateTime(local.date, time))
        return instant.toDailyHours()
    }

    @Composable
    fun BacAreaPlot(
        scope: XYGraphScope<Float, Float>,
        now: Instant,
        color: Color = MaterialTheme.colorScheme.primary,
    ) {
        scope.AreaPlot(pastEvents(now), color = color, alpha = 0.8f)
        scope.AreaPlot(futureEvents(now), color = color, alpha = 0.3f)
    }

    companion object {
        fun smoothed(events: List<AlcoholAtTime>, resolution: Duration = 10.minutes): BacGraphData {
            return BacGraphData(BacEventSmoother.smooth(events, resolution))
        }
    }
}

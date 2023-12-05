package fi.tuska.beerclock.bac

import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.inHours
import io.github.koalaplot.core.line.Point
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.max
import kotlin.time.Duration.Companion.hours

class BacCalculation(sortedInputDrinks: List<DrinkRecordInfo>) : KoinComponent {

    private val prefs: GlobalUserPreferences = get()
    private val times = DrinkTimeService()
    private val dayStart = times.dayStartTime()
    private val strings = Strings.get()

    /**
     * List of the different BAC levels at different points during the day, measured in
     * grams of unburned alcohol in your body.
     * Includes events at the start and end of the day, recording the states there.
     */
    private val events: List<AlcoholAtTime>

    init {
        val (before, today) = sortedInputDrinks.partition { it.time < dayStart }
        val alcoholAtDayStart = AlcoholRemaining.forDrinks(before, dayStart)
        events = AlcoholBurnCalculation.calculate(alcoholAtDayStart, today)
    }

    fun atTime(time: Instant): AlcoholAtTime {
        val futureIdx = events.indexOfFirst { it.time > time }
        if (futureIdx <= 0) return AlcoholAtTime(time, 0.0)
        return events[futureIdx - 1].interpolate(events[futureIdx], time)
    }

    /**
     * The maximum amount of alcohol on the system during the entire day.
     */
    private val maxAlcoholConcentration = events.maxByOrNull { it.alcoholGrams }
        ?.let { BacFormulas.bloodAlcoholConcentration(it.alcoholGrams, prefs.prefs) } ?: 0.0

    private fun dailyHourLabel(hour: Float) =
        times.toLocalDateTime(dayStart + hour.toDouble().hours)

    private inline fun Instant.toDailyHours(): Float = (this - dayStart).inHours().toFloat()

    fun asBacGraph() = GraphDefinition(
        xRange = 0f..24f,
        yRange = 0f..max(1.0, maxAlcoholConcentration + 0.1).toFloat(),
        xTitle = strings.home.bacTime,
        yTitle = strings.home.bacPermilles,
        formatXLabel = { strings.time(dailyHourLabel(it)) + " " }
    )

    private inline fun AlcoholAtTime.toGraphPoint(): Point<Float, Float> = Point(
        time.toDailyHours(),
        BacFormulas.bloodAlcoholConcentration(alcoholGrams, prefs.prefs).toFloat()
    )

    fun pastGraphEvents(now: Instant): List<Point<Float, Float>> =
        (events.filter { it.time <= now } + atTime(now)).map { it.toGraphPoint() }

    fun futureGraphEvents(now: Instant): List<Point<Float, Float>> =
        (listOf(atTime(now)) + events.filter { it.time > now }).map { it.toGraphPoint() }
}

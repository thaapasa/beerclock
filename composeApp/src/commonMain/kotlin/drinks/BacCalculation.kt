package fi.tuska.beerclock.drinks

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
    private val dayEnd = dayStart + 24.hours
    private val strings = Strings.get()

    /**
     * List of the different BAC levels at different points during the day, measured in
     * grams of unburned alcohol in your body.
     * Includes events at the start and end of the day, recording the states there.
     */
    private val events: List<AlcoholAtTime>

    init {
        val (before, today) = sortedInputDrinks.partition { it.time < dayStart }
        val alcoholAtDayStart = AlcoholBurnCalculation.forDrinks(before, dayStart)
        val calc = AlcoholBurnCalculation(dayStart, alcoholAtDayStart)
        today.forEach {
            calc.update(it)
        }
        calc.update(dayEnd, 0.0)
        events = calc.list()
    }

    /**
     * The maximum amount of alcohol on the system during the entire day.
     */
    val maxAlcoholConcentration = events.maxByOrNull { it.alcoholGrams }
        ?.let { AlcoholCalculator.bloodAlcoholConcentration(it.alcoholGrams, prefs.prefs) } ?: 0.0

    private fun dailyHourLabel(hour: Float) = times.toLocalDateTime(dayStart + hour.toInt().hours)
    private fun Instant.toDailyHours(): Float = (this - dayStart).inHours().toFloat()

    fun asBacGraph() = GraphDefinition(
        xRange = 0f..24f,
        yRange = 0f..max(1.0, maxAlcoholConcentration + 0.1).toFloat(),
        xTitle = strings.home.bacTime,
        yTitle = strings.home.bacPermilles,
        formatXLabel = { strings.time(dailyHourLabel(it)) + " " }
    )

    fun asGraphEvents(): List<Point<Float, Float>> =
        events.map {
            Point(
                it.time.toDailyHours(),
                AlcoholCalculator.bloodAlcoholConcentration(it.alcoholGrams, prefs.prefs).toFloat()
            )
        }
}

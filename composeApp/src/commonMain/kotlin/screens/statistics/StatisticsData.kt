package fi.tuska.beerclock.screens.statistics

import fi.tuska.beerclock.drinks.StatisticsByCategory
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import io.github.koalaplot.core.xygraph.Point
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.max

data class StatisticsData(
    val period: StatisticsPeriod,
    val categoryStatistics: StatisticsByCategory,
    val unitsByDate: Map<LocalDate, Double>,
) : KoinComponent {
    val prefs: GlobalUserPreferences = get()
    val strings = Strings.get()
    private val days = period.range.lengthInDays(false)
    private val maxUnits =
        max(unitsByDate.maxBy { it.value }.value + 0.5, prefs.prefs.maxDailyUnits)

    init {
        getLogger("Stats").info("Days: $days, maxUnits: $maxUnits")
    }

    fun graphDef() = GraphDefinition(
        xRange = -1f..days.toFloat() + 1f,
        yRange = 0f..maxUnits.toFloat(),
        xTitle = "Päivä",
        yTitle = "Annoksia",
    )

    fun unitsByDays(): List<Point<Float, Float>> = unitsByDate.entries.mapIndexed { idx, entry ->
        Point(
            idx.toFloat(),
            entry.value.toFloat()
        )
    }

}

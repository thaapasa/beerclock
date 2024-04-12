package fi.tuska.beerclock.screens.statistics

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.StatisticsByCategory
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.settings.UserPreferences
import fi.tuska.beerclock.util.toList
import fi.tuska.beerclock.util.toWeekOfYear
import io.github.koalaplot.core.xygraph.LinearAxisModel
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import kotlin.math.max

abstract class StatisticsData(
    val period: StatisticsPeriod,
    val categoryStatistics: StatisticsByCategory,
) : KoinComponent {
    abstract val xValues: List<Float>
    abstract val yValues: List<Float>
    abstract fun graphDef(): GraphDefinition

    abstract fun maxBarValue(): Float

    @Composable
    fun barColor(index: Int): Color = when {
        index >= 0 && index < yValues.size && yValues[index] < maxBarValue() -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.tertiary
    }
}

abstract class DailyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
    private val maxDailyUnits: Double,
) : StatisticsData(period, categoryStatistics) {
    private val days = period.range.lengthInDays(false)
    private val maxUnits =
        max(units.maxBy { it.second }.second, maxDailyUnits) + 0.5

    override val xValues = List(units.size) { it.toFloat() }
    override val yValues = units.map { it.second.toFloat() }

    abstract fun formatXLabel(x: Float): String

    override fun maxBarValue() = maxDailyUnits.toFloat()

    override fun graphDef(): GraphDefinition {
        val strings = Strings.get()
        return GraphDefinition(
            xAxisModel = LinearAxisModel(-0.5f..days.toFloat() - 0.5f),
            yAxisModel = LinearAxisModel(0f..maxUnits.toFloat()),
            xTitle = strings.statistics.dayLabel,
            yTitle = strings.statistics.unitsLabel,
            formatXLabel = this::formatXLabel,
            formatYLabel = ::formatYLabel,
        )
    }
}

class WeeklyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
    maxDailyUnits: Double,
) : DailyStatisticsData(period, categoryStatistics, units, maxDailyUnits) {
    val times = DrinkTimeService()
    private val firstWeekDay = times.firstDayOfCurrentWeek()
    private val weekDays =
        (firstWeekDay..<firstWeekDay.plus(DatePeriod(days = 7))).toList().map { it.dayOfWeek }

    override fun formatXLabel(x: Float): String = when (val d = x.toInt()) {
        in 0..6 -> "${Strings.get().weekdayShort(weekDays[d])} "
        else -> "$x"
    }
}

class MonthlyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
    maxDailyUnits: Double,
) : DailyStatisticsData(period, categoryStatistics, units, maxDailyUnits) {
    override fun formatXLabel(x: Float): String = "${x.toInt() + 1}. "
}

class YearlyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
    private val maxWeeklyUnits: Double,
) : StatisticsData(period, categoryStatistics) {
    val strings = Strings.get()
    private val byWeek = units.groupBy { it.first.toWeekOfYear() }
    private val weeks = byWeek.keys.toList()
    override val xValues = List(weeks.size) { it.toFloat() }
    override val yValues =
        List(weeks.size) { w -> byWeek[weeks[w]]?.sumOf { it.second }?.toFloat() ?: 0f }
    private val maxUnits = max(yValues.max(), maxWeeklyUnits.toFloat()) + 0.5f;

    override fun maxBarValue() = maxWeeklyUnits.toFloat()

    override fun graphDef() = GraphDefinition(
        xAxisModel = LinearAxisModel(-0.5f..weeks.size - 0.5f),
        yAxisModel = LinearAxisModel(0f..maxUnits),
        xTitle = strings.statistics.weekTitle,
        yTitle = strings.statistics.unitsLabel,
        formatXLabel = {
            when (val w = it.toInt()) {
                in weeks.indices -> {
                    val week = weeks[w]
                    "${week.weekNumber} "
                }

                else -> "? "
            }
        },
        formatYLabel = ::formatYLabel,
    )
}

fun toStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
    prefs: UserPreferences,
): StatisticsData = when (period.period) {
    StatisticsPeriodType.WEEK -> WeeklyStatisticsData(
        period,
        categoryStatistics,
        units,
        maxDailyUnits = prefs.maxDailyUnits
    )

    StatisticsPeriodType.MONTH -> MonthlyStatisticsData(
        period,
        categoryStatistics,
        units,
        maxDailyUnits = prefs.maxDailyUnits
    )

    StatisticsPeriodType.YEAR -> YearlyStatisticsData(
        period,
        categoryStatistics,
        units,
        maxWeeklyUnits = prefs.maxWeeklyUnits
    )
}

fun formatYLabel(y: Float): String {
    val formatted = "$y "
    return if (formatted.endsWith(".0 ")) "${y.toInt()} " else formatted
}

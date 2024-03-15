package fi.tuska.beerclock.screens.statistics

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.StatisticsByCategory
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.toList
import fi.tuska.beerclock.util.toWeekOfYear
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.max

abstract class StatisticsData(
    val period: StatisticsPeriod,
    val categoryStatistics: StatisticsByCategory,
) : KoinComponent {
    protected val prefs: GlobalUserPreferences = get()

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
) : StatisticsData(period, categoryStatistics), KoinComponent {
    protected val strings = Strings.get()
    private val days = period.range.lengthInDays(false)
    private val maxUnits =
        max(units.maxBy { it.second }.second, prefs.prefs.maxDailyUnits) + 0.5

    init {
        getLogger("Stats").info("Days: $days, maxUnits: $maxUnits")
    }

    override val xValues = List(units.size) { it.toFloat() }
    override val yValues = units.map { it.second.toFloat() }

    abstract fun formatXLabel(x: Float): String

    override fun maxBarValue() = prefs.prefs.maxDailyUnits.toFloat()

    override fun graphDef() = GraphDefinition(
        xRange = -0.5f..days.toFloat() - 0.5f,
        yRange = 0f..maxUnits.toFloat(),
        xTitle = strings.statistics.dayLabel,
        yTitle = strings.statistics.unitsLabel,
        formatXLabel = this::formatXLabel,
        formatYLabel = ::formatYLabel,
    )
}

class WeeklyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
) : DailyStatisticsData(period, categoryStatistics, units) {
    val times = DrinkTimeService()
    private val firstWeekDay = times.firstDayOfCurrentWeek()
    private val weekDays =
        (firstWeekDay..<firstWeekDay.plus(DatePeriod(days = 7))).toList().map { it.dayOfWeek }

    override fun formatXLabel(x: Float): String = when (val d = x.toInt()) {
        in 0..6 -> "${strings.weekdayShort(weekDays[d])} "
        else -> "$x"
    }
}

class MonthlyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
) : DailyStatisticsData(period, categoryStatistics, units) {
    override fun formatXLabel(x: Float): String = "${x.toInt() + 1}. "
}

class YearlyStatisticsData(
    period: StatisticsPeriod,
    categoryStatistics: StatisticsByCategory,
    units: List<Pair<LocalDate, Double>>,
) : StatisticsData(period, categoryStatistics) {
    val strings = Strings.get()
    private val byWeek = units.groupBy { it.first.toWeekOfYear() }
    private val weeks = byWeek.keys.toList()
    override val xValues = List(weeks.size) { it.toFloat() }
    override val yValues =
        List(weeks.size) { w -> byWeek[weeks[w]]?.sumOf { it.second }?.toFloat() ?: 0f }
    private val maxUnits = max(yValues.max(), prefs.prefs.maxWeeklyUnits.toFloat()) + 0.5f;

    override fun maxBarValue() = prefs.prefs.maxWeeklyUnits.toFloat()

    override fun graphDef() = GraphDefinition(
        xRange = -0.5f..weeks.size - 0.5f,
        yRange = 0f..maxUnits,
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
): StatisticsData = when (period.period) {
    StatisticsPeriodType.WEEK -> WeeklyStatisticsData(period, categoryStatistics, units)
    StatisticsPeriodType.MONTH -> MonthlyStatisticsData(period, categoryStatistics, units)
    StatisticsPeriodType.YEAR -> YearlyStatisticsData(period, categoryStatistics, units)
}

fun formatYLabel(y: Float): String {
    val formatted = "$y "
    return if (formatted.endsWith(".0 ")) "${y.toInt()} " else formatted
}

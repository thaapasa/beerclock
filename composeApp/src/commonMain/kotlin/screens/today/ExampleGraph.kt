package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import io.github.koalaplot.core.line.AreaBaseline
import io.github.koalaplot.core.line.AreaStyle
import io.github.koalaplot.core.line.DefaultPoint
import io.github.koalaplot.core.line.LineChart
import io.github.koalaplot.core.theme.KoalaPlotTheme
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xychart.LineStyle
import io.github.koalaplot.core.xychart.LinearAxisModel
import io.github.koalaplot.core.xychart.TickPosition
import io.github.koalaplot.core.xychart.XYChart
import io.github.koalaplot.core.xychart.rememberAxisStyle
import org.koin.core.component.KoinComponent
import kotlin.math.sin
import kotlin.time.Duration.Companion.hours


class GraphVM : ViewModel(), KoinComponent {
    private val strings = Strings.get()
    private val drinkTimes = DrinkTimeService()
    val range = 0f..24f
    val start = drinkTimes.dayStartTime()
    val startDate = drinkTimes.toLocalDate(start)
    fun hourToTime(hours: Float) = drinkTimes.toLocalDateTime(start + hours.toInt().hours)

    fun labelFor(hour: Float): String = strings.time(hourToTime(hour)) + " "
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ExampleGraph() {

    val vm = rememberWithDispose { GraphVM() }

    val data = (0..(vm.range.endInclusive.toInt() / 3) + 1).map {
        DefaultPoint(
            it.toFloat() * 3,
            (sin((it * 0.8).toFloat()) / 2.7 + 0.53).toFloat(),
        )
    }.toList()

    val data1 = data.take(6)
    val data2 = data.takeLast(data.size - 5)

    val gridLineColor = MaterialTheme.colorScheme.onSurfaceVariant
    Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)) {
        KoalaPlotTheme(
            sizes = KoalaPlotTheme.sizes.copy(gap = 0.dp),
            axis = KoalaPlotTheme.axis.copy(
                color = gridLineColor,
                minorTickSize = 0.dp,
                majorTickSize = 0.dp,
                minorGridlineStyle = LineStyle(
                    SolidColor(gridLineColor),
                    strokeWidth = 0.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f))
                ),
                majorGridlineStyle = LineStyle(
                    SolidColor(gridLineColor),
                    strokeWidth = 0.dp,
                )
            )
        ) {
            XYChart(
                xAxisTitle = "Aika",
                xAxisModel = LinearAxisModel(
                    range = vm.range,
                    minimumMajorTickIncrement = 2f,
                    minimumMajorTickSpacing = 30.dp,
                    minorTickCount = 1
                ),
                xAxisLabels = vm::labelFor,
                xAxisStyle = rememberAxisStyle(
                    color = gridLineColor,
                    labelRotation = 90,
                    tickPosition = TickPosition.Outside,
                    lineWidth = 0.dp
                ),
                yAxisModel = LinearAxisModel(range = 0f..1.0f),
                yAxisLabels = { "$it " },
                yAxisTitle = "Alkoholia veressä ‰"
            ) {
                LineChart(
                    data = data1,
                    lineStyle = LineStyle(
                        brush = SolidColor(MaterialTheme.colorScheme.primary),
                        strokeWidth = 2.dp
                    ),
                    areaStyle = AreaStyle(
                        brush = SolidColor(MaterialTheme.colorScheme.primary),
                        alpha = 0.66f
                    ),
                    areaBaseline = AreaBaseline.ConstantLine(0f)
                )
                LineChart(
                    data = data2,
                    lineStyle = LineStyle(
                        brush = SolidColor(MaterialTheme.colorScheme.primary),
                        strokeWidth = 2.dp
                    ),
                    areaStyle = AreaStyle(
                        brush = SolidColor(MaterialTheme.colorScheme.primary),
                        alpha = 0.33f
                    ),
                    areaBaseline = AreaBaseline.ConstantLine(0f)
                )
            }
        }
    }
}
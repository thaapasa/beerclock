package fi.tuska.beerclock.screens.today

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.line.DefaultPoint
import io.github.koalaplot.core.line.LineChart
import io.github.koalaplot.core.theme.KoalaPlotTheme
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xychart.LineStyle
import io.github.koalaplot.core.xychart.LinearAxisModel
import io.github.koalaplot.core.xychart.XYChart
import kotlin.math.pow

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ExampleGraph() {

    val data1 = (1..10).map { DefaultPoint(it.toFloat(), 10f * (1.04).pow(it).toFloat()) }.toList()
    val data2 = (1..10).map { DefaultPoint(it.toFloat(), 10f * (1.06).pow(it).toFloat()) }.toList()

    KoalaPlotTheme(
        axis = KoalaPlotTheme.axis.copy(
            minorGridlineStyle = KoalaPlotTheme.axis.minorGridlineStyle!!.copy(
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f))
            )
        )
    ) {

        XYChart(
            xAxisModel = LinearAxisModel(range = 1f..10f),
            yAxisModel = LinearAxisModel(range = 1f..20f)
        ) {
            LineChart(
                data = data1,
                lineStyle = LineStyle(brush = SolidColor(Color(0xFF00498F)), strokeWidth = 2.dp),
            )
            LineChart(
                data = data2,
                lineStyle = LineStyle(brush = SolidColor(Color(0xFF00498F)), strokeWidth = 2.dp),
            )
        }
    }
}
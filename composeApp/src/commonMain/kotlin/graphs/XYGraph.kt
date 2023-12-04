package fi.tuska.beerclock.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xychart.LinearAxisModel
import io.github.koalaplot.core.xychart.TickPosition
import io.github.koalaplot.core.xychart.XYChart
import io.github.koalaplot.core.xychart.XYChartScope
import io.github.koalaplot.core.xychart.rememberAxisStyle

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun XYGraph(graph: GraphDefinition, content: @Composable XYChartScope<Float, Float>.() -> Unit) {
    XYChart(
        xAxisTitle = graph.xTitle,
        xAxisModel = LinearAxisModel(
            range = graph.xRange,
            minimumMajorTickIncrement = 2f,
            minimumMajorTickSpacing = 30.dp,
            minorTickCount = 1
        ),
        xAxisLabels = graph::formatXLabel,
        xAxisStyle = rememberAxisStyle(
            color = gridLineColor(),
            labelRotation = 90,
            tickPosition = TickPosition.Outside,
            lineWidth = 0.dp
        ),
        yAxisTitle = graph.yTitle,
        yAxisModel = LinearAxisModel(range = graph.yRange),
        yAxisLabels = graph::formatYLabel,
        xAxisStyle = rememberAxisStyle(
            color = gridLineColor(),
            tickPosition = TickPosition.Outside,
            lineWidth = 0.dp
        ),
        content = content
    )
}

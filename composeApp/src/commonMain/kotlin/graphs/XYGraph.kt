package fi.tuska.beerclock.graphs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.theme.KoalaPlotTheme
import io.github.koalaplot.core.util.VerticalRotation
import io.github.koalaplot.core.util.rotateVertically
import io.github.koalaplot.core.xychart.LinearAxisModel
import io.github.koalaplot.core.xychart.TickPosition
import io.github.koalaplot.core.xychart.XYChart
import io.github.koalaplot.core.xychart.XYChartScope
import io.github.koalaplot.core.xychart.rememberAxisStyle

@Composable
fun XYGraph(
    graph: GraphDefinition,
    modifier: Modifier = Modifier,
    content: @Composable XYChartScope<Float, Float>.() -> Unit
) {
    val labelColor = graphLabelColor()
    val gridColor = gridLineColor()
    val xAxisModel = remember(graph.xRange) {
        LinearAxisModel(
            range = graph.xRange,
            minimumMajorTickIncrement = 2f,
            minimumMajorTickSpacing = 30.dp,
            minorTickCount = 1
        )
    }
    val yAxisModel = remember(graph.yRange) {
        LinearAxisModel(range = graph.yRange)
    }
    XYChart(
        xAxisModel,
        yAxisModel,
        modifier,
        xAxisStyle = rememberAxisStyle(
            color = gridColor,
            labelRotation = 90,
            tickPosition = TickPosition.Outside,
            lineWidth = 0.dp
        ),
        xAxisLabels = {
            Text(
                graph.formatXLabel(it),
                color = labelColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(end = 2.dp)
            )
        },
        xAxisTitle = {
            Text(
                graph.xTitle,
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = KoalaPlotTheme.sizes.gap)
            )
        },
        yAxisStyle = rememberAxisStyle(
            color = gridColor,
        ),
        yAxisLabels = {
            Text(
                graph.formatYLabel(it),
                color = labelColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .rotateVertically(VerticalRotation.COUNTER_CLOCKWISE)
                    .padding(bottom = KoalaPlotTheme.sizes.gap)
            )
        },
        yAxisTitle = {
            Text(
                graph.yTitle,
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
                    .rotateVertically(VerticalRotation.COUNTER_CLOCKWISE),
            )
        },
        // It seems we need to include these definitions to be able to call this overload
        // that allows fine-tuning title rendering
        horizontalMajorGridLineStyle = KoalaPlotTheme.axis.majorGridlineStyle,
        horizontalMinorGridLineStyle = KoalaPlotTheme.axis.minorGridlineStyle,
        verticalMajorGridLineStyle = KoalaPlotTheme.axis.majorGridlineStyle,
        verticalMinorGridLineStyle = KoalaPlotTheme.axis.minorGridlineStyle,
        panZoomEnabled = false,
        content
    )
}

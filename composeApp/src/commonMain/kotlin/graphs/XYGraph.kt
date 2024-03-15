package fi.tuska.beerclock.graphs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.style.KoalaPlotTheme
import io.github.koalaplot.core.util.VerticalRotation
import io.github.koalaplot.core.util.rotateVertically
import io.github.koalaplot.core.xygraph.TickPosition
import io.github.koalaplot.core.xygraph.XYGraphScope
import io.github.koalaplot.core.xygraph.rememberAxisStyle
import io.github.koalaplot.core.xygraph.rememberLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph as KoalaXYGraph

@Composable
fun XYGraph(
    graph: GraphDefinition,
    modifier: Modifier = Modifier,
    content: @Composable XYGraphScope<Float, Float>.() -> Unit,
) {
    val labelColor = graphLabelColor()
    val gridColor = gridLineColor()
    val xAxisModel = rememberLinearAxisModel(range = graph.xRange)
    val yAxisModel = rememberLinearAxisModel(range = graph.yRange)
    KoalaXYGraph(
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
                modifier = Modifier.padding(end = 2.dp),
            )
        },
        xAxisTitle = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    graph.xTitle,
                    color = labelColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = KoalaPlotTheme.sizes.gap),
                )
            }
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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .rotateVertically(VerticalRotation.COUNTER_CLOCKWISE),
            )
        },
        // It seems we need to include these definitions to be able to call this overload
        // that allows fine-tuning title rendering
        horizontalMajorGridLineStyle = KoalaPlotTheme.axis.majorGridlineStyle,
        horizontalMinorGridLineStyle = KoalaPlotTheme.axis.minorGridlineStyle,
        verticalMajorGridLineStyle = NoLine,
        verticalMinorGridLineStyle = NoLine,
        panZoomEnabled = false,
        content
    )
}

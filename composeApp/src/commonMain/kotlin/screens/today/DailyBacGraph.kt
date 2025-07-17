package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.graphs.XYGraph
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.util.ZeroHour
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.xygraph.HorizontalLineAnnotation
import io.github.koalaplot.core.xygraph.Point
import io.github.koalaplot.core.xygraph.XYGraphScope
import kotlin.time.Clock
import kotlin.time.Instant


@Composable
fun DailyBacGraph(
    bac: BacStatus,
    now: Instant = Clock.System.now(),
    modifier: Modifier = Modifier,
) {
    val graph = bac.graphData
    Card(modifier = modifier.fillMaxWidth().height(280.dp)) {
        XYGraph(graph = graph.graphDef(), modifier = Modifier.padding(4.dp), showYGridLines = true)
        {
            DaySeparator(this, graph.toGraphX(ZeroHour) + 24f, graph.maxY)
            DrivingLimit(this, bac.drivingLimitBac().toFloat(), maxX = 24f, maxY = graph.maxY)
            graph.BacAreaPlot(this, now)
        }
    }
}


@Composable
fun DaySeparator(scope: XYGraphScope<Float, Float>, x: Float, maxY: Float) {
    scope.LinePlot(
        data = listOf(Point(x, 0.0f), Point(x, maxY * 0.9f)),
        lineStyle = LineStyle(
            brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
            strokeWidth = 1.dp,
            alpha = 0.5f
        ),
    )
    scope.LinePlot(
        data = listOf(Point(x, maxY * 0.95f)),
        symbol = {
            AppIcon.MOON.icon(
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp).alpha(0.8f),
            )
        }
    )
}

@Composable
fun DrivingLimit(scope: XYGraphScope<Float, Float>, yPos: Float, maxX: Float, maxY: Float) {
    val yOffs = maxY * 0.025f / yPos
    scope.HorizontalLineAnnotation(
        location = yPos,
        lineStyle = LineStyle(
            brush = SolidColor(MaterialTheme.colorScheme.tertiary),
            strokeWidth = 2.dp,
            alpha = 0.8f
        ),
    )
    scope.LinePlot(
        data = listOf(Point(maxX - 0.7f, yPos + yOffs)),
        symbol = {
            AppIcon.CAR.icon(
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(14.dp).alpha(0.8f),
            )
        }
    )
}

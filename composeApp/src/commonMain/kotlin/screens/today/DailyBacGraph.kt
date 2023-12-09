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
import io.github.koalaplot.core.line.LineChart
import io.github.koalaplot.core.line.Point
import io.github.koalaplot.core.xychart.LineStyle
import io.github.koalaplot.core.xychart.XYChartScope
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


@Composable
fun DailyBacGraph(
    bac: BacStatus,
    now: Instant = Clock.System.now(),
    modifier: Modifier = Modifier
) {
    val graph = bac.graphData
    Card(modifier = modifier.fillMaxWidth().height(240.dp)) {
        XYGraph(graph = graph.graphDef(), modifier = Modifier.padding(4.dp))
        {
            DaySeparator(this, graph.toGraphX(ZeroHour) + 24f, graph.maxY)
            DrivingLimit(this, bac.drivingLimitBac().toFloat(), 24f)
            graph.drawAreas(this, now)
        }
    }
}


@Composable
fun DaySeparator(scope: XYChartScope<Float, Float>, x: Float, maxY: Float) {
    scope.LineChart(
        data = listOf(Point(x, 0.0f), Point(x, maxY * 0.9f)),
        lineStyle = LineStyle(
            brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
            strokeWidth = 1.dp,
            alpha = 0.5f
        ),
    )
    scope.LineChart(
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
fun DrivingLimit(scope: XYChartScope<Float, Float>, yPos: Float, maxX: Float) {
    scope.LineChart(
        data = listOf(Point(0f, yPos), Point(maxX, yPos)),
        lineStyle = LineStyle(
            brush = SolidColor(MaterialTheme.colorScheme.tertiary),
            strokeWidth = 2.dp,
            alpha = 0.8f
        ),
    )
    scope.LineChart(
        data = listOf(Point(maxX - 0.7f, yPos + 0.04f)),
        symbol = {
            AppIcon.CAR.icon(
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(14.dp).alpha(0.8f),
            )
        }
    )
}

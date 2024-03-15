package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.graphs.XYGraph
import io.github.koalaplot.core.bar.DefaultVerticalBar
import io.github.koalaplot.core.bar.VerticalBarPlot

@Composable
fun StatisticsGraph(data: StatisticsData) {
    Row(
        modifier = Modifier.fillMaxWidth().height(240.dp)
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    ) {
        XYGraph(graph = data.graphDef(), modifier = Modifier.padding(4.dp).fillMaxWidth())
        {
            VerticalBarPlot(
                xData = data.xValues,
                yData = data.yValues,
                bar = { DefaultVerticalBar(SolidColor(data.barColor(it))) })
        }
    }
}

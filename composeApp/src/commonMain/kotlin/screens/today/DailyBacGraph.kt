package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.graphs.AreaChart
import fi.tuska.beerclock.graphs.XYGraph
import kotlinx.datetime.Clock


@Composable
fun DailyBacGraph(bac: BacStatus, modifier: Modifier = Modifier) {
    val now = Clock.System.now()
    Card(modifier = modifier.fillMaxWidth().height(200.dp)) {
        XYGraph(graph = bac.asBacGraph(), modifier = Modifier.padding(4.dp))
        {
            AreaChart(
                bac.pastInstantGraphEvents(now),
                color = MaterialTheme.colorScheme.primary,
                alpha = 0.8f
            )
            AreaChart(
                bac.futureInstantGraphEvents(now),
                color = MaterialTheme.colorScheme.primary,
                alpha = 0.3f
            )
            AreaChart(
                bac.pastSmoothGraphEvents(now),
                color = MaterialTheme.colorScheme.tertiary,
                alpha = 0.8f
            )
            AreaChart(
                bac.futureSmoothGraphEvents(now),
                color = MaterialTheme.colorScheme.tertiary,
                alpha = 0.3f
            )
        }
    }
}

package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.BacCalculation
import fi.tuska.beerclock.graphs.AreaChart
import fi.tuska.beerclock.graphs.XYGraph
import kotlinx.datetime.Clock


@Composable
fun DailyBacGraph(bac: BacCalculation, modifier: Modifier = Modifier) {
    val now = Clock.System.now()
    Card(modifier = modifier.fillMaxWidth().height(200.dp)) {
        XYGraph(graph = bac.asBacGraph(), modifier = Modifier.padding(4.dp))
        {
            AreaChart(bac.pastGraphEvents(now))
            AreaChart(bac.futureGraphEvents(now), alpha = 0.5f)
        }
    }
}

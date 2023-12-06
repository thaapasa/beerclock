package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.graphs.XYGraph
import kotlinx.datetime.Clock


@Composable
fun DailyBacGraph(bac: BacStatus, modifier: Modifier = Modifier) {
    val now = Clock.System.now()
    val graph = bac.graphData
    Card(modifier = modifier.fillMaxWidth().height(200.dp)) {
        XYGraph(graph = graph.graphDef(), modifier = Modifier.padding(4.dp))
        {
            graph.drawAreas(this, now)
        }
    }
}

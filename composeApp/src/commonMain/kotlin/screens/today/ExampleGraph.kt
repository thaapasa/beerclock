package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.graphs.AreaChart
import fi.tuska.beerclock.graphs.GraphDefinition
import fi.tuska.beerclock.graphs.XYGraph
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import io.github.koalaplot.core.line.DefaultPoint
import org.koin.core.component.KoinComponent
import kotlin.math.sin
import kotlin.time.Duration.Companion.hours


class GraphVM : ViewModel(), KoinComponent {
    private val strings = Strings.get()
    private val drinkTimes = DrinkTimeService()
    val start = drinkTimes.dayStartTime()
    val startDate = drinkTimes.toLocalDate(start)
    fun hourToTime(hours: Float) = drinkTimes.toLocalDateTime(start + hours.toInt().hours)

    fun graph(): GraphDefinition {
        return GraphDefinition(
            xRange = 0f..24f,
            yRange = 0f..1f,
            xTitle = "Aika",
            yTitle = "Alkoholia veressä ‰",
            formatXLabel = { strings.time(hourToTime(it)) + " " }
        )
    }
}

@Composable
fun ExampleGraph() {

    val vm = rememberWithDispose { GraphVM() }
    val graph = vm.graph()

    val data = (0..(graph.xRange.endInclusive.toInt() / 3) + 1).map {
        DefaultPoint(
            it.toFloat() * 3,
            (sin((it * 0.8).toFloat()) / 2.7 + 0.53).toFloat(),
        )
    }.toList()

    val data1 = data.take(6)
    val data2 = data.takeLast(data.size - 5)

    Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)) {
        XYGraph(graph = graph)
        {
            AreaChart(data1)
            AreaChart(data2, alpha = 0.5f)
        }
    }
}
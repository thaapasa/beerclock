package fi.tuska.beerclock.graphs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.koalaplot.core.bar.BarScope
import io.github.koalaplot.core.bar.DefaultVerticalBarPlotEntry
import io.github.koalaplot.core.bar.DefaultVerticalBarPosition
import io.github.koalaplot.core.bar.GroupedVerticalBarPlot
import io.github.koalaplot.core.bar.VerticalBarPlotEntry
import io.github.koalaplot.core.bar.VerticalBarPlotGroupedPointEntry
import io.github.koalaplot.core.bar.VerticalBarPosition
import io.github.koalaplot.core.style.KoalaPlotTheme
import io.github.koalaplot.core.xygraph.XYGraphScope

/**
 * BUG FIX: The KoalaPlot library seems to have a bug in how they store the graph data
 * internally. This file is a copy of KoalaPlot source code and should be removed once
 * KoalaPlot gets the code fixed.
 */

@Composable
fun <X> XYGraphScope<X, Float>.VerticalBarPlot(
    xData: List<X>,
    yData: List<Float>,
    modifier: Modifier = Modifier,
    bar: @Composable BarScope.(index: Int) -> Unit,
    barWidth: Float = 0.9f,
    animationSpec: AnimationSpec<Float> = KoalaPlotTheme.animationSpec,
) {
    require(xData.size == yData.size) { "xData and yData must be the same size." }
    VerticalBarPlot(
        xData.mapIndexed { index, x ->
            DefaultVerticalBarPlotEntry(x, DefaultVerticalBarPosition(0f, yData[index]))
        },
        modifier,
        bar,
        barWidth,
        animationSpec
    )
}

@Composable
fun <X, Y, E : VerticalBarPlotEntry<X, Y>> XYGraphScope<X, Y>.VerticalBarPlot(
    data: List<E>,
    modifier: Modifier = Modifier,
    bar: @Composable BarScope.(index: Int) -> Unit,
    barWidth: Float = 0.9f,
    animationSpec: AnimationSpec<Float> = KoalaPlotTheme.animationSpec,
) {
    // BUG FIX: Original code does not have the data parameter here for the remember function
    val dataAdapter = remember(data) {
        EntryToGroupedEntryListAdapter(data)
    }

    // Delegate to the GroupedVerticalBarPlot - non-grouped is like grouped but with only 1 group per x-axis position
    GroupedVerticalBarPlot(
        dataAdapter,
        modifier = modifier,
        bar = { dataIndex, _, _ ->
            bar(dataIndex)
        },
        maxBarGroupWidth = barWidth,
        animationSpec = animationSpec
    )
}


private class EntryToGroupedEntryListAdapter<X, Y>(
    val data: List<VerticalBarPlotEntry<X, Y>>,
) : AbstractList<VerticalBarPlotGroupedPointEntry<X, Y>>() {
    override val size: Int
        get() = data.size

    override fun get(index: Int): VerticalBarPlotGroupedPointEntry<X, Y> {
        return EntryToGroupedEntryAdapter(data[index])
    }
}

private class EntryToGroupedEntryAdapter<X, Y>(val entry: VerticalBarPlotEntry<X, Y>) :
    VerticalBarPlotGroupedPointEntry<X, Y> {
    override val x: X = entry.x
    override val y: List<VerticalBarPosition<Y>>
        get() = object : AbstractList<VerticalBarPosition<Y>>() {
            override val size: Int = 1
            override fun get(index: Int): VerticalBarPosition<Y> = entry.y
        }
}



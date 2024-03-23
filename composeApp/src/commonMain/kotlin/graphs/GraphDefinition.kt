package fi.tuska.beerclock.graphs

import io.github.koalaplot.core.xygraph.AxisModel

private fun defaultLabelFormatter(value: Float): String = "$value "

data class GraphDefinition(
    val xAxisModel: AxisModel<Float>,
    val yAxisModel: AxisModel<Float>,
    val xTitle: String,
    val yTitle: String,
    val formatXLabel: (value: Float) -> String = ::defaultLabelFormatter,
    val formatYLabel: (value: Float) -> String = ::defaultLabelFormatter,
)

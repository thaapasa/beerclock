package fi.tuska.beerclock.graphs

private fun defaultLabelFormatter(value: Float): String = "$value "

data class GraphDefinition(
    val xRange: ClosedFloatingPointRange<Float>,
    val yRange: ClosedFloatingPointRange<Float>,
    val xTitle: String,
    val yTitle: String,
    val formatXLabel: (value: Float) -> String = ::defaultLabelFormatter,
    val formatYLabel: (value: Float) -> String = ::defaultLabelFormatter,
)

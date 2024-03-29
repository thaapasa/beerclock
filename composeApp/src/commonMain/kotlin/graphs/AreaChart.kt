package fi.tuska.beerclock.graphs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.line.AreaBaseline
import io.github.koalaplot.core.line.AreaPlot
import io.github.koalaplot.core.style.AreaStyle
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.xygraph.Point
import io.github.koalaplot.core.xygraph.XYGraphScope

@Composable
fun XYGraphScope<Float, Float>.AreaPlot(
    data: List<Point<Float, Float>>,
    color: Color = MaterialTheme.colorScheme.primary,
    alpha: Float = 1f,
) {
    AreaPlot(
        data = data,
        lineStyle = LineStyle(
            brush = SolidColor(color),
            strokeWidth = 2.dp,
            alpha = alpha
        ),
        areaStyle = AreaStyle(
            brush = SolidColor(color),
            alpha = alpha * 0.66f
        ),
        areaBaseline = AreaBaseline.ConstantLine(0f)
    )
}

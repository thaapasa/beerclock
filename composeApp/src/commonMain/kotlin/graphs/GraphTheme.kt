package fi.tuska.beerclock.graphs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.theme.KoalaPlotTheme
import io.github.koalaplot.core.xychart.LineStyle

@Composable
inline fun gridLineColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
inline fun graphLabelColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun GraphTheme(content: @Composable () -> Unit) {
    val grindLIneC = gridLineColor()

    KoalaPlotTheme(
        sizes = KoalaPlotTheme.sizes.copy(gap = 4.dp),
        axis = KoalaPlotTheme.axis.copy(
            color = grindLIneC,
            minorTickSize = 0.dp,
            majorTickSize = 0.dp,
            minorGridlineStyle = LineStyle(
                SolidColor(grindLIneC),
                strokeWidth = 0.dp,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f))
            ),
            majorGridlineStyle = LineStyle(
                SolidColor(grindLIneC),
                strokeWidth = 0.dp,
            )
        ),
        content = content
    )
}

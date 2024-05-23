package fi.tuska.beerclock.graphs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.style.KoalaPlotTheme
import io.github.koalaplot.core.style.LineStyle

@Composable
fun gridLineColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun graphLabelColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun GraphTheme(content: @Composable () -> Unit) {
    val gridLineC = gridLineColor()

    KoalaPlotTheme(
        sizes = KoalaPlotTheme.sizes.copy(gap = 4.dp),
        axis = KoalaPlotTheme.axis.copy(
            color = gridLineC,
            minorTickSize = 0.dp,
            majorTickSize = 0.dp,
            minorGridlineStyle = LineStyle(
                SolidColor(gridLineC),
                strokeWidth = 0.dp,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f))
            ),
            majorGridlineStyle = LineStyle(
                SolidColor(gridLineC),
                strokeWidth = 0.dp,
            )
        ),
        content = content
    )
}

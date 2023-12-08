package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

val gapDegrees = 60.0f

@Composable
fun Gauge(
    position: Double,
    value: String,
    iconPainter: Painter? = null,
    icon: @Composable (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.aspectRatio(1f).background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = CircleShape
        )
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(6.dp)) {
            drawArc(
                color = color,
                alpha = 0.2f,
                startAngle = 90.0f + gapDegrees / 2.0f,
                sweepAngle = 360.0f - gapDegrees,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round),
            )
            drawArc(
                color = color,
                startAngle = 90.0f + gapDegrees / 2.0f,
                sweepAngle = ((360.0 - gapDegrees) * position).toFloat(),
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round),
            )
        }
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center),
        )
        (iconPainter ?: icon)?.let {
            Box(
                modifier = Modifier.fillMaxWidth(0.25f)
                    .align(Alignment.BottomCenter)
            ) {
                iconPainter?.let {
                    Icon(
                        contentDescription = "Gauge icon",
                        painter = it,
                        tint = color
                    )
                }
                icon?.invoke()
            }
        }
    }
}

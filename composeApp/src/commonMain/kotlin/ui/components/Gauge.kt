package fi.tuska.beerclock.ui.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import kotlin.math.max
import kotlin.math.min

const val gapDegrees = 60.0f

class GaugeValue(
    val icon: @Composable (() -> Unit)? = null,
    val appIcon: AppIcon? = null,
    maxValue: Double = 1.0,
) {
    var value by mutableStateOf(0.0)
        private set
    private var maxValue by mutableStateOf(max(maxValue, 0.1))

    fun position(): Double {
        return min(value / maxValue, 1.0)
    }

    fun setValue(value: Double, maxValue: Double?) {
        this.value = value
        if (maxValue != null) {
            this.maxValue = max(maxValue, 0.1)
        }
    }
}

@Composable
fun Gauge(
    value: GaugeValue,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
) {
    Gauge(
        position = value.position().toFloat(),
        value = value.value.toFloat(),
        icon = value.icon,
        iconPainter = value.appIcon?.painter(),
        color = color,
        modifier = modifier
    )
}

@Composable
fun Gauge(
    position: Float,
    value: Float,
    iconPainter: Painter? = null,
    icon: @Composable (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
) {
    val v = animateFloatAsState(targetValue = value)
    val p = animateFloatAsState(targetValue = position)
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
                sweepAngle = ((360.0f - gapDegrees) * p.value),
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round),
            )
        }
        Text(
            text = Strings.get().dec1F(v.value.toDouble()),
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

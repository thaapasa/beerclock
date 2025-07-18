package fi.tuska.beerclock.wear.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import fi.tuska.beerclock.R
import kotlin.math.max
import kotlin.math.min

const val gapDegrees = 60.0f

open class GaugeValue(
    initialValue: Double = 0.0,
    @param:DrawableRes @field:DrawableRes val iconRes: Int,
    maxValue: Double = 1.0,
) {
    var value by mutableDoubleStateOf(initialValue)
        private set
    private var maxValue by mutableDoubleStateOf(max(maxValue, 0.1))

    fun position(): Double {
        return min(value / maxValue, 1.0)
    }

    fun isOverLimit(): Boolean {
        return value > maxValue
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
    @StringRes valueRes: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
) {
    Gauge(
        position = value.position().toFloat(),
        value = value.value.toFloat(),
        isOverLimit = value.isOverLimit(),
        iconRes = value.iconRes,
        valueRes = valueRes,
        labelRes = labelRes,
        modifier = modifier
    )
}

@Composable
fun Gauge(
    position: Float,
    value: Float,
    @DrawableRes iconRes: Int,
    @StringRes valueRes: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    isOverLimit: Boolean = false,
) {
    val v = animateFloatAsState(targetValue = value, label = "Value")
    val p = animateFloatAsState(targetValue = position, label = "Position")
    val arcColor =
        if (isOverLimit) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    val arcBgColor = MaterialTheme.colors.surface
    val textColor =
        if (isOverLimit) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
    Box(
        modifier = modifier.aspectRatio(1f)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            drawArc(
                color = arcBgColor,
                startAngle = -90.0f + gapDegrees / 2.0f,
                sweepAngle = 360.0f - gapDegrees,
                useCenter = false,
                style = Stroke(width = 6f, cap = StrokeCap.Round),
            )
            drawArc(
                color = arcColor,
                startAngle = -90.0f + gapDegrees / 2.0f,
                sweepAngle = ((360.0f - gapDegrees) * p.value),
                useCenter = false,
                style = Stroke(width = 6f, cap = StrokeCap.Round),
            )
        }
        Text(
            text = stringResource(valueRes, v.value),
            style = MaterialTheme.typography.caption2,
            color = textColor,
            modifier = Modifier.align(Alignment.Center),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .align(Alignment.TopCenter)
        ) {
            Icon(
                painter = painterResource(iconRes),
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(labelRes)
            )
        }

    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    val gauge = GaugeValue(initialValue = 0.4, maxValue = 1.4, iconRes = R.drawable.ic_permille)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Gauge(
            gauge,
            valueRes = R.string.bac_complication_value,
            labelRes = R.string.bac_complication_label,
            modifier = Modifier
                .size(52.dp)
                .align(Alignment.Center)
        )
    }
}

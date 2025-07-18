package fi.tuska.beerclock.wear.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.presentation.components.Gauge
import fi.tuska.beerclock.wear.presentation.components.GaugeValue
import fi.tuska.beerclock.wear.presentation.components.adaptiveIconPainterResource
import fi.tuska.beerclock.wear.presentation.theme.BeerclockTheme
import androidx.wear.tooling.preview.devices.WearDevices

@Composable
fun MainScreen(vm: BeerWearViewModel) {
    MainScreen(bacGauge = vm.bacGauge, dailyUnitsGauge = vm.dailyUnitsGauge)
}

@Composable
fun MainScreen(bacGauge: GaugeValue, dailyUnitsGauge: GaugeValue) {
    BeerclockTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Box(
                modifier = Modifier
                    .padding(top = 42.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(64.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Image(
                    painter = adaptiveIconPainterResource(R.mipmap.ic_launcher),
                    contentDescription = "fo",
                    modifier = Modifier.clip(
                        RoundedCornerShape(50)
                    )
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(52.dp)
                    .align(Alignment.CenterStart)
            ) {
                Gauge(
                    value = bacGauge,
                    labelRes = R.string.bac_complication_label,
                    valueRes = R.string.bac_complication_value
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(52.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Gauge(
                    value = dailyUnitsGauge,
                    labelRes = R.string.daily_units_complication_label,
                    valueRes = R.string.daily_units_complication_value
                )
            }
        }

    }
}


@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    val bacGauge =
        GaugeValue(initialValue = 0.7, maxValue = 1.4, iconRes = R.drawable.ic_permille)
    val dailyUnitsGauge =
        GaugeValue(initialValue = 1.5, maxValue = 7.0, iconRes = R.drawable.ic_local_bar)
    MainScreen(bacGauge = bacGauge, dailyUnitsGauge = dailyUnitsGauge)
}

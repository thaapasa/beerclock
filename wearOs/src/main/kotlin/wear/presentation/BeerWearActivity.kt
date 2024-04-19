/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package fi.tuska.beerclock.wear.presentation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import fi.tuska.beerclock.R
import fi.tuska.beerclock.wear.presentation.components.Gauge
import fi.tuska.beerclock.wear.presentation.components.GaugeValue
import fi.tuska.beerclock.wear.presentation.components.adaptiveIconPainterResource
import fi.tuska.beerclock.wear.presentation.theme.BeerclockTheme

class BeerWearActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }


    companion object {
        fun getComplicationTapIntent(context: Context, complicationId: Int): PendingIntent {
            val intent = Intent(context, BeerWearActivity::class.java)

            // Pass complicationId as the requestCode to ensure that different complications get
            // different intents.
            return PendingIntent.getActivity(
                context,
                complicationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }
}

@Composable
fun WearApp() {
    val bacGauge = remember {
        GaugeValue(initialValue = 0.7, maxValue = 1.4, iconRes = R.drawable.ic_permille)
    }
    val unitsGauge = remember {
        GaugeValue(initialValue = 1.5, maxValue = 7.0, iconRes = R.drawable.ic_local_bar)
    }
    LaunchedEffect(bacGauge) {
        bacGauge.setValue(0.8, maxValue = 1.4)
    }
    LaunchedEffect(unitsGauge) {
        unitsGauge.setValue(2.5, maxValue = 7.0)
    }
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
                    value = unitsGauge,
                    labelRes = R.string.daily_units_complication_label,
                    valueRes = R.string.daily_units_complication_value
                )
            }
        }

    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}

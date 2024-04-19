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
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class BeerWearActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        val vm by viewModels<BeerWearViewModel>()

        setContent {
            MainScreen(vm)
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

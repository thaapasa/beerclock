package fi.tuska.beerclock.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.database.DatabaseInfo
import fi.tuska.beerclock.images.AppImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.localization.TextWithLinks
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.getAppVersion
import fi.tuska.beerclock.util.getDeviceModel
import fi.tuska.beerclock.util.getOSVersion
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object AboutScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> AboutPage(innerPadding) },
            title = Strings.get().about.title
        )
    }
}

@Composable
fun AboutPage(innerPadding: PaddingValues) {
    val strings = Strings.get()
    val vm = rememberWithDispose { AboutViewModel() }

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Image(
                painter = AppImage.APP_ICON.painter(),
                modifier = Modifier.weight(0.4f).aspectRatio(1f).padding(16.dp)
                    .clip(RoundedCornerShape(32.dp)),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Feature"
            )
            Column(
                modifier = Modifier.weight(0.6f).fillMaxHeight().padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(strings.appName, style = MaterialTheme.typography.headlineMedium)
                Text(vm.appVersion, style = MaterialTheme.typography.headlineSmall)
            }
        }
        InfoItem(strings.about.deviceModel, vm.deviceModel)
        InfoItem(strings.about.osVersion, vm.osVersion)
        InfoItem(strings.about.sqliteVersion, vm.dbInfo.sqliteVersion)
        InfoItem(strings.about.dbVersion, vm.dbInfo.databaseVersion.toString())
        Spacer(modifier = Modifier.height(16.dp))
        TextWithLinks(strings.about.aboutText)
    }
}


class AboutViewModel : ViewModel(), KoinComponent {
    val dbInfo: DatabaseInfo = get()
    val appVersion = getAppVersion()
    val deviceModel = getDeviceModel()
    val osVersion = getOSVersion()
}


@Composable
fun InfoItem(title: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
    ) {
        Text(title, modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}

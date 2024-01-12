package fi.tuska.beerclock.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.database.DatabaseInfo
import fi.tuska.beerclock.localization.Strings
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
        InfoItem(strings.about.appVersion, vm.appVersion)
        InfoItem(strings.about.deviceModel, vm.deviceModel)
        InfoItem(strings.about.osVersion, vm.osVersion)
        InfoItem(strings.about.sqliteVersion, vm.dbInfo.sqliteVersion)
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

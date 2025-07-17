package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.CommonParcelize

@CommonParcelize
object SettingsScreen : ParcelableScreen {
    
    @Composable
    override fun Content() {
        val snackbar = SnackbarHostState()
        SubLayout(
            content = { innerPadding -> SettingsPage(innerPadding, snackbar = snackbar) },
            title = Strings.get().settings.title,
            snackbarHostState = snackbar
        )
    }
}

@Composable
fun SettingsPage(innerPadding: PaddingValues, snackbar: SnackbarHostState) {
    val vm = rememberWithDispose { SettingsViewModel(snackbar) }

    vm.trackChanges()
    val scrollState = rememberScrollState()

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth().verticalScroll(scrollState),
    ) {
        SettingsTabs(
            vm,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        when (vm.settingsTab) {
            SettingsTabs.USER -> UserSettings(vm)
            SettingsTabs.DRINK -> DrinkSettings(vm)
            SettingsTabs.IMPORT -> DataImportSettings(vm)
        }

        Spacer(Modifier.height(16.dp))
    }
}

package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> SettingsPage(innerPadding) },
            title = Strings.get().settings.title
        )
    }
}

@Composable
fun SettingsPage(innerPadding: PaddingValues) {
    val vm = rememberWithDispose { SettingsViewModel() }

    vm.trackChanges()
    val scrollState = rememberScrollState()

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth().verticalScroll(scrollState),
    ) {
        SettingsTabs(
            selected = vm.settingsTab,
            modifier = Modifier.padding(bottom = 32.dp),
            onSelect = { vm.settingsTab = it }
        )

        when (vm.settingsTab) {
            SettingsTabs.USER -> UserSettings(vm)
            SettingsTabs.DRINK -> DrinkSettings(vm)
            SettingsTabs.IMPORT -> DataImportSettings(vm)
        }

        Spacer(Modifier.height(16.dp))
    }
}

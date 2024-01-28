package fi.tuska.beerclock.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.ui.components.Tab
import fi.tuska.beerclock.ui.components.TabSwitcher

enum class SettingsTabs { USER, DRINK, IMPORT }

val UserSettingsTab = Tab({ it.settings.userSettingsTitle }, SettingsTabs.USER)
val DrinkSettingsTab = Tab({ it.settings.drinkSettingsTitle }, SettingsTabs.DRINK)
val ImportTab = Tab({ it.settings.dataImportTitle }, SettingsTabs.IMPORT)

@Composable
internal fun SettingsTabs(
    vm: SettingsViewModel,
    modifier: Modifier = Modifier,
) {
    val tabs =
        listOf(UserSettingsTab, DrinkSettingsTab) + (if (vm.showImportTab) listOf(
            ImportTab
        ) else listOf())

    TabSwitcher(
        tabs = tabs,
        modifier = modifier,
        selectedTab = vm.settingsTab,
        onClick = { tab -> vm.settingsTab = tab.data })
}

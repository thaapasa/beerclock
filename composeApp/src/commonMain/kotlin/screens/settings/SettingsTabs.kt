package fi.tuska.beerclock.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.backup.isJAlcoMeterImportSupported
import fi.tuska.beerclock.ui.components.Tab
import fi.tuska.beerclock.ui.components.TabSwitcher

enum class SettingsTabs { USER, DRINK, IMPORT }

val UserSettingsTab = Tab({ it.settings.userSettingsTitle }, SettingsTabs.USER)
val DrinkSettingsTab = Tab({ it.settings.drinkSettingsTitle }, SettingsTabs.DRINK)
val ImportTab = Tab({ it.settings.dataImportTitle }, SettingsTabs.IMPORT)

@Composable
fun SettingsTabs(
    selected: SettingsTabs,
    onSelect: (tab: SettingsTabs) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs =
        listOf(UserSettingsTab, DrinkSettingsTab) + (if (isJAlcoMeterImportSupported()) listOf(
            ImportTab
        ) else listOf())

    TabSwitcher(
        tabs = tabs,
        modifier = modifier,
        selectedTab = selected,
        onClick = { tab -> onSelect(tab.data) })
}

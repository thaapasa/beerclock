package fi.tuska.beerclock.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.ui.components.Tab
import fi.tuska.beerclock.ui.components.TabSwitcher

enum class SettingsTabs { USER, DRINK }

val UserSettingsTab = Tab({ it.settings.userSettingsTitle }, SettingsTabs.USER)
val DrinkSettingsTab = Tab({ it.settings.drinkSettingsTitle }, SettingsTabs.DRINK)

private val tabs = listOf(UserSettingsTab, DrinkSettingsTab)

@Composable
fun SettingsTabs(
    selected: SettingsTabs,
    onSelect: (tab: SettingsTabs) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabSwitcher(
        tabs = tabs,
        modifier = modifier,
        selectedTab = selected,
        onClick = { tab -> onSelect(tab.data) })
}

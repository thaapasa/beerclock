package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DropdownSelect
import fi.tuska.beerclock.ui.theme.ThemeSelection

private val ThemeOptions: Array<ThemeSelection> = arrayOf(*ThemeSelection.values())

@Composable
fun ThemeDropdown(
    selected: ThemeSelection,
    onSelect: (value: ThemeSelection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    DropdownSelect(
        options = ThemeOptions,
        selected = selected,
        onSelect = {
            onSelect(it)
        },
        modifier = modifier,
        valueToText = { strings.themeName(it) },
        label = { Text(text = strings.settings.themeLabel) },
    )
}


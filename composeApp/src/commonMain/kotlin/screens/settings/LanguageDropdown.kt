package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.localization.AppLocale
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DropdownSelect

val options: Array<AppLocale?> = arrayOf(null, *AppLocale.values())

@Composable
fun LanguageDropdown(
    selected: AppLocale?,
    onSelect: (value: AppLocale?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    DropdownSelect(
        options = options,
        selected = selected,
        onSelect = {
            onSelect(it)
        },
        modifier = modifier,
        valueToText = { it?.language ?: strings.settings.phoneLocale },
        label = { Text(text = strings.settings.localeLabel) },
    )
}


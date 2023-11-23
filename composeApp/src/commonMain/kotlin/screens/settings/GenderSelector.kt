package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.settings.Gender
import fi.tuska.beerclock.ui.components.DropdownSelect

@Composable
fun GenderSelector(
    selected: Gender,
    onSelect: (gender: Gender) -> Unit,
    modifier: Modifier = Modifier
) = DropdownSelect(
    options = Gender.values(),
    selected = selected,
    onSelect = onSelect,
    modifier = modifier,
    valueToText = strings::forGender,
    placeholder = { Text(text = strings.settings.genderLabel) },
    label = { Text(text = strings.settings.genderLabel) },
    iconForValue = {
        Icon(
            painter = AppIcon.forGender(it).painter(),
            contentDescription = strings.forGender(it)
        )
    }
)

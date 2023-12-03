package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.SingleUnitWeights
import fi.tuska.beerclock.images.FlagIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DropdownSelect

val countrySelections = SingleUnitWeights.keys.toTypedArray()

@Composable
fun AlcoholUnitsDropdown(
    onSelect: (value: Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    val options = remember {
        val opts = countrySelections.copyOf()
        opts.sortBy(strings::countryName)
        opts
    }
    var selected by remember { mutableStateOf("") }
    DropdownSelect(
        options = options,
        selected = selected,
        onSelect = {
            SingleUnitWeights[it]?.let { w -> onSelect(w) }
            selected = it
        },
        modifier = modifier,
        valueToText = {
            if (it.isNotEmpty()) strings.settings.alcoholGramsByCountryOption(it)
            else strings.settings.pickCountry
        },
        placeholder = { Text(text = strings.settings.pickCountry) },
        label = { Text(text = strings.settings.alcoholGramsByCountry) },
        iconForValue = { FlagIcon.forCountry(it)?.image() },
        showIcon = { it != "" }
    )
}


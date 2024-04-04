package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.images.FlagImage
import fi.tuska.beerclock.localization.Country
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DropdownSelect

val countrySelections = Country.entries

@Composable
fun AlcoholUnitsDropdown(
    onSelect: (value: Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    val options = remember {
        val opts = countrySelections.toList()
        opts.sortedBy(strings::countryName)
        (listOf(null) + opts).toTypedArray()
    }
    var selected by remember { mutableStateOf<Country?>(null) }
    DropdownSelect(
        options = options,
        selected = selected,
        onSelect = {
            it?.let { onSelect(it.standardUnitWeightGrams) }
            selected = it
        },
        modifier = modifier,
        valueToText = {
            it?.let { strings.settings.alcoholGramsByCountryOption(it) }
                ?: strings.settings.pickCountry
        },
        placeholder = { Text(text = strings.settings.pickCountry) },
        label = { Text(text = strings.settings.alcoholGramsByCountry) },
        iconForValue = { it?.let { c -> FlagImage(c) } },
        showIcon = { it != null },
    )
}


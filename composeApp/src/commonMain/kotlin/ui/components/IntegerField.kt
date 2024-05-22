package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.util.safeToInt

@Composable
fun IntegerField(
    value: Int,
    onValueChange: (value: Int) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    errorText: String? = null,
) {
    val strings = Strings.get()
    OutlinedConvertedTextEditor(
        value,
        onValueChange,
        valueToString = { it.toString() },
        valueFromString = ::safeToInt,
        leadingIcon = leadingIcon,
        label = label,
        supportingText = supportingText,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        errorText = errorText ?: strings.errors.invalidInteger,
    )
}

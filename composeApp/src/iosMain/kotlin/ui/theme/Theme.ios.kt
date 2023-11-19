package fi.tuska.beerclock.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
@Suppress("UNUSED")
actual fun getDynamicTheme(useDarkTheme: Boolean, fallback: ColorScheme): ColorScheme {
    return fallback
}

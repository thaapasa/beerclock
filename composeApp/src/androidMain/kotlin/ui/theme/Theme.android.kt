package fi.tuska.beerclock.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
@Suppress("UNUSED")
actual fun getDynamicTheme(useDarkTheme: Boolean, fallback: ColorScheme): ColorScheme {
    val dynamicSupport = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        !dynamicSupport -> fallback
        useDarkTheme -> dynamicDarkColorScheme(LocalContext.current)
        else -> dynamicLightColorScheme(LocalContext.current)
    }
}

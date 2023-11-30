package fi.tuska.beerclock

import android.content.Context
import androidx.startup.Initializer
import fi.tuska.beerclock.settings.PreferenceProvider

/**
 * This is initialized on app startup by the androidx.startup:startup-runtime
 * library. See the provider definition in AndroidManifest.xml.
 */
@Suppress("unused")
class ContextInitializer : Initializer<PreferenceProvider> {
    override fun create(context: Context): PreferenceProvider {
        PreferenceProvider.applicationContext = context.applicationContext
        return PreferenceProvider
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

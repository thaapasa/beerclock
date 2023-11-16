package fi.tuska.beerclock.common

import android.content.Context
import androidx.startup.Initializer
import fi.tuska.beerclock.common.database.DriverFactory
import fi.tuska.beerclock.common.settings.PreferenceProvider

/**
 * This is initialized on app startup by the androidx.startup:startup-runtime
 * library. See the provider definition in AndroidManifest.xml.
 */
@Suppress("unused")
class ContextInitializer : Initializer<PreferenceProvider> {
    override fun create(context: Context): PreferenceProvider {
        PreferenceProvider.applicationContext = context.applicationContext
        DriverFactory.applicationContext = context.applicationContext
        return PreferenceProvider
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

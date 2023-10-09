package fi.tuska.beerclock.common.settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.startup.Initializer

actual object PreferenceProvider {
    /** Initialized by PreferenceInitializer */
    lateinit var applicationContext: Context
        internal set

    actual fun getPrefs(): PreferenceStore {
        val prefs = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return Prefs(prefs)
    }
}

class Prefs constructor(private val prefs: SharedPreferences) : PreferenceStore {

    private val tag = "Prefs"

    override fun getString(key: String, defaultValue: String): String {
        val p = prefs.getString(key, defaultValue) ?: defaultValue
        Log.i(tag, "Found value $p for $key (default $defaultValue)")
        return p
    }

    override fun setString(key: String, value: String) {
        Log.i(tag, "Storing $key = $value")
        with(prefs.edit()) {
            putString(key, value)
            apply()
        }
    }
}

/**
 * This is initialized on app startup by the androidx.startup:startup-runtime
 * library. See the provider definition in AndroidManifest.xml.
 */
@Suppress("unused")
class PreferenceInitializer: Initializer<PreferenceProvider> {
    override fun create(context: Context): PreferenceProvider {
        PreferenceProvider.applicationContext = context.applicationContext
        return PreferenceProvider
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
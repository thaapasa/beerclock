package fi.tuska.beerclock.settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

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
        Log.d(tag, "Found value $p for $key (default $defaultValue)")
        return p
    }

    override fun setString(key: String, value: String) {
        Log.d(tag, "Storing $key = $value")
        with(prefs.edit()) {
            putString(key, value)
            apply()
        }
    }
}

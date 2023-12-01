package fi.tuska.beerclock.settings

import android.content.Context
import android.util.Log

class AndroidPreferenceStore(applicationContext: Context) : PreferenceStore {

    private val prefs = applicationContext.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )

    private val tag = "Prefs"

    override fun getString(key: String, defaultValue: String): String {
        val p = prefs.getString(key, defaultValue) ?: defaultValue
        Log.d(
            tag,
            "Found value $p for $key (default $defaultValue), running in ${Thread.currentThread().name}"
        )
        return p
    }

    override fun setString(key: String, value: String) {
        Log.d(tag, "Storing $key = $value on ${Thread.currentThread().name}")
        with(prefs.edit()) {
            putString(key, value)
            apply()
        }
    }
}

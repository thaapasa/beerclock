package fi.tuska.beerclock.settings

import platform.Foundation.NSUserDefaults

actual object PreferenceProvider {

    actual fun getPrefs(): PreferenceStore {
        return Prefs()
    }
}

private class Prefs : PreferenceStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    override fun getString(key: String, defaultValue: String): String =
        userDefaults.stringForKey(key) ?: defaultValue

    override fun setString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }
}
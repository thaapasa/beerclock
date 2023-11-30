package fi.tuska.beerclock.settings

import platform.Foundation.NSUserDefaults

class IosPreferenceStore() : PreferenceStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    override fun getString(key: String, defaultValue: String): String =
        userDefaults.stringForKey(key) ?: defaultValue

    override fun setString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }
}
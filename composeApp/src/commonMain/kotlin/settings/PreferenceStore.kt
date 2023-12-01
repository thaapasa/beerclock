package fi.tuska.beerclock.settings

/**
 * Generic class to store preferences (app settings).
 * Implementing interfaces are injected by Koin.
 */
interface PreferenceStore {
    fun getString(key: String, defaultValue: String): String
    fun setString(key: String, value: String)
}

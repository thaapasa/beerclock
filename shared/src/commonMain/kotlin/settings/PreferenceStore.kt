package fi.tuska.beerclock.common.settings

interface PreferenceStore {
    fun getString(key: String, defaultValue: String): String
    fun setString(key: String, value: String)
}

expect object PreferenceProvider {
    fun getPrefs(): PreferenceStore
}

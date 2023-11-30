package fi.tuska.beerclock.settings

interface PreferenceStore {
    fun getString(key: String, defaultValue: String): String
    fun setString(key: String, value: String)
}

package fi.tuska.beerclock.localization

enum class AppLocale(val language: String) {
    FI("fi"), EN("en");

    companion object {
        fun forName(name: String): AppLocale? = try {
            AppLocale.valueOf(name)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

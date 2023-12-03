package fi.tuska.beerclock.localization

enum class AppLocale(
    val language: String,
    val nameEn: String,
    val nameFi: String,
    val country: Country
) {
    FI("fi", "Finnish", "Suomi", Country.FI),
    EN("en", "English", "Englanti", Country.GB);

    companion object {
        fun forName(name: String): AppLocale? = try {
            AppLocale.valueOf(name)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

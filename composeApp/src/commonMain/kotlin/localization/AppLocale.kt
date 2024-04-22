package fi.tuska.beerclock.localization

import androidx.compose.ui.text.intl.Locale

enum class AppLocale(
    val language: String,
    val nameEn: String,
    val nameFi: String,
    val country: Country,
    val locale: Locale,
) {
    FI("fi", "Finnish", "Suomi", Country.FI, Locale("fi")),
    EN("en", "English", "Englanti", Country.GB, Locale("en"));

    companion object {
        fun forName(name: String): AppLocale? = try {
            AppLocale.valueOf(name)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

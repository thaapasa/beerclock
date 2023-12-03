package fi.tuska.beerclock.localization

enum class Country(
    val nameEn: String,
    val nameFi: String,
    val flagPath: String,
    val standardUnitWeightGrams: Double
) {
    AT("Austria", "Itävalta", "drawable/flags/at.webp", 20.0),
    AU("Australia", "Australia", "drawable/flags/au.webp", 10.0),
    CA("Canada", "Kanada", "drawable/flags/ca.webp", 13.5),
    DK("Denmark", "Tanska", "drawable/flags/dk.webp", 12.0),
    ES("Spain", "Espanja", "drawable/flags/es.webp", 10.0),
    FI("Finland", "Suomi", "drawable/flags/fi.webp", 12.0),
    FR("France", "Ranska", "drawable/flags/fr.webp", 10.0),
    GB("United Kingdom", "Iso-Britannia", "drawable/flags/gb.webp", 8.0),
    HU("Hungary", "Unkari", "drawable/flags/hu.webp", 17.0),
    IE("Ireland", "Irlanti", "drawable/flags/ie.webp", 10.0),
    IS("Iceland", "Islanti", "drawable/flags/is.webp", 8.0),
    IT("Italy", "Italia", "drawable/flags/it.webp", 12.0),
    JP("Japan", "Japani", "drawable/flags/jp.webp", 19.75),
    NL("Netherlands", "Alankomaat", "drawable/flags/nl.webp", 10.0),
    NZ("New Zealand", "Uusi-Seelanti", "drawable/flags/nz.webp", 10.0),
    PL("Poland", "Puola", "drawable/flags/pl.webp", 10.0),
    PT("Portugal", "Portugali", "drawable/flags/pt.webp", 11.0),
    SE("Sweden", "Ruotsi", "drawable/flags/se.webp", 12.0),
    US("United States", "Yhdysvallat", "drawable/flags/us.webp", 14.0);

    companion object {
        fun forIsoCode(isoCountryCode: String): Country? = try {
            Country.valueOf(isoCountryCode)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}
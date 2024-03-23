package fi.tuska.beerclock.localization

import beerclock.composeapp.generated.resources.Res
import beerclock.composeapp.generated.resources.flag_at
import beerclock.composeapp.generated.resources.flag_au
import beerclock.composeapp.generated.resources.flag_ca
import beerclock.composeapp.generated.resources.flag_dk
import beerclock.composeapp.generated.resources.flag_es
import beerclock.composeapp.generated.resources.flag_fi
import beerclock.composeapp.generated.resources.flag_fr
import beerclock.composeapp.generated.resources.flag_gb
import beerclock.composeapp.generated.resources.flag_hu
import beerclock.composeapp.generated.resources.flag_ie
import beerclock.composeapp.generated.resources.flag_is
import beerclock.composeapp.generated.resources.flag_it
import beerclock.composeapp.generated.resources.flag_jp
import beerclock.composeapp.generated.resources.flag_nl
import beerclock.composeapp.generated.resources.flag_nz
import beerclock.composeapp.generated.resources.flag_pl
import beerclock.composeapp.generated.resources.flag_pt
import beerclock.composeapp.generated.resources.flag_se
import beerclock.composeapp.generated.resources.flag_us
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
enum class Country(
    val nameEn: String,
    val nameFi: String,
    val drawable: DrawableResource,
    val standardUnitWeightGrams: Double,
) {
    AT("Austria", "Itävalta", Res.drawable.flag_at, 20.0),
    AU("Australia", "Australia", Res.drawable.flag_au, 10.0),
    CA("Canada", "Kanada", Res.drawable.flag_ca, 13.5),
    DK("Denmark", "Tanska", Res.drawable.flag_dk, 12.0),
    ES("Spain", "Espanja", Res.drawable.flag_es, 10.0),
    FI("Finland", "Suomi", Res.drawable.flag_fi, 12.0),
    FR("France", "Ranska", Res.drawable.flag_fr, 10.0),
    GB("United Kingdom", "Iso-Britannia", Res.drawable.flag_gb, 8.0),
    HU("Hungary", "Unkari", Res.drawable.flag_hu, 17.0),
    IE("Ireland", "Irlanti", Res.drawable.flag_ie, 10.0),
    IS("Iceland", "Islanti", Res.drawable.flag_is, 8.0),
    IT("Italy", "Italia", Res.drawable.flag_it, 12.0),
    JP("Japan", "Japani", Res.drawable.flag_jp, 19.75),
    NL("Netherlands", "Alankomaat", Res.drawable.flag_nl, 10.0),
    NZ("New Zealand", "Uusi-Seelanti", Res.drawable.flag_nz, 10.0),
    PL("Poland", "Puola", Res.drawable.flag_pl, 10.0),
    PT("Portugal", "Portugali", Res.drawable.flag_pt, 11.0),
    SE("Sweden", "Ruotsi", Res.drawable.flag_se, 12.0),
    US("United States", "Yhdysvallat", Res.drawable.flag_us, 14.0);

    companion object {
        fun forIsoCode(isoCountryCode: String): Country? = try {
            Country.valueOf(isoCountryCode)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}

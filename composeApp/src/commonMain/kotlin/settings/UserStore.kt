package fi.tuska.beerclock.settings

import fi.tuska.beerclock.localization.AppLocale
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.theme.ThemeSelection
import fi.tuska.beerclock.util.fromPrefsString
import fi.tuska.beerclock.util.safeToDouble
import fi.tuska.beerclock.util.toPrefsString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class UserStore : KoinComponent {
    private val store: PreferenceStore = get()
    private val prefs: GlobalUserPreferences = get()

    suspend fun setLocale(locale: AppLocale?) {
        if (prefs.prefs.locale == locale) {
            return
        }
        setState { copy(locale = locale) }
        setStateValue(PreferenceKeys.locale, locale?.name ?: "")
    }

    suspend fun setTheme(theme: ThemeSelection) {
        if (prefs.prefs.theme == theme) {
            return
        }
        setState { copy(theme = theme) }
        setStateValue(PreferenceKeys.theme, theme.name)
    }

    suspend fun setDynamicPalette(dynamicPalette: Boolean) {
        if (prefs.prefs.dynamicPalette == dynamicPalette) {
            return
        }
        setState { copy(dynamicPalette = dynamicPalette) }
        setStateValue(PreferenceKeys.dynamicPalette, dynamicPalette.toString())
    }

    suspend fun setHasAgreedDisclosure(hasAgreed: Boolean) {
        if (prefs.prefs.hasAgreedDisclosure == hasAgreed) {
            return
        }
        setState { copy(hasAgreedDisclosure = hasAgreed) }
        setStateValue(PreferenceKeys.hasAgreedDisclosure, hasAgreed.toString())
    }

    suspend fun setGender(gender: Gender) {
        if (prefs.prefs.gender == gender) {
            return
        }
        setState { copy(gender = gender) }
        setStateValue(PreferenceKeys.gender, gender.toString())
    }

    suspend fun setWeight(weight: Double) {
        if (prefs.prefs.weightKg == weight) {
            return
        }
        setState { copy(weightKg = weight) }
        setStateValue(PreferenceKeys.weight, weight.toString())
    }

    suspend fun setStartOfDay(startOfDay: LocalTime) {
        if (prefs.prefs.startOfDay == startOfDay) {
            return
        }
        setState { copy(startOfDay = startOfDay) }
        setStateValue(PreferenceKeys.startOfDay, startOfDay.toPrefsString())
    }

    suspend fun setAlcoholGramsInUnit(gramsInUnit: Double) {
        if (prefs.prefs.alchoholGramsInUnit == gramsInUnit) {
            return
        }
        setState { copy(alchoholGramsInUnit = gramsInUnit) }
        setStateValue(PreferenceKeys.alchoholGramsInUnit, gramsInUnit.toString())
    }

    suspend fun setDrivingLimitBac(drivingLimitBac: Double) {
        if (prefs.prefs.drivingLimitBac == drivingLimitBac) {
            return
        }
        setState { copy(drivingLimitBac = drivingLimitBac) }
        setStateValue(PreferenceKeys.drivingLimitBac, drivingLimitBac.toString())
    }

    suspend fun setMAXBac(bac: Double) {
        if (prefs.prefs.maxBac == bac) {
            return
        }
        setState { copy(maxBac = bac) }
        setStateValue(PreferenceKeys.maxBac, bac.toString())
    }

    suspend fun setMaxDailyUnits(units: Double) {
        if (prefs.prefs.maxDailyUnits == units) {
            return
        }
        setState { copy(maxDailyUnits = units) }
        setStateValue(PreferenceKeys.maxDailyUnits, units.toString())
    }

    suspend fun setMaxWeeklyUnits(units: Double) {
        if (prefs.prefs.maxWeeklyUnits == units) {
            return
        }
        setState { copy(maxWeeklyUnits = units) }
        setStateValue(PreferenceKeys.maxWeeklyUnits, units.toString())
    }

    private suspend fun setStateValue(stateKey: String, stringified: String) {
        withContext(Dispatchers.IO) {
            store.setString(stateKey, stringified)
        }
    }

    private inline fun setState(update: UserPreferences.() -> UserPreferences) {
        prefs.update(prefs.prefs.update())
    }

    object PreferenceKeys {
        const val locale = "prefs.user.locale"
        const val theme = "prefs.user.theme"
        const val hasAgreedDisclosure = "prefs.user.hasAgreedDisclosure"
        const val dynamicPalette = "prefs.user.dynamicPalette"
        const val weight = "prefs.user.weight"
        const val gender = "prefs.user.gender"
        const val startOfDay = "prefs.user.startOfDay"
        const val alchoholGramsInUnit = "prefs.user.alchoholGramsInUnit"
        const val drivingLimitBac = "prefs.user.drivingLimitBac"
        const val maxBac = "prefs.user.maxBAC"
        const val maxDailyUnits = "prefs.user.maxDailyUnits"
        const val maxWeeklyUnits = "prefs.user.maxWeeklyUnits"
    }

    companion object {
        /**
         * Loads the user preferences from the preference store. Falls back to defaults
         * in case values are missing or if invalid values are stored to the preferences.
         *
         * This is called once during app startup. Further changes are automatically applied
         * to the global user prefs state managed by Koin.
         *
         * For simplicity and ease-of-use, this is now just performed on the main UI thread.
         */
        fun load(store: PreferenceStore): UserPreferences {
            val defaults = UserPreferences()

            val genderStr = store.getString(PreferenceKeys.gender, defaults.gender.toString())
            val startOfDayStr =
                store.getString(PreferenceKeys.startOfDay, defaults.startOfDay.toPrefsString())
            val localeStr = store.getString(PreferenceKeys.locale, defaults.locale?.name ?: "")
            val themeStr = store.getString(PreferenceKeys.theme, defaults.theme.name)

            return UserPreferences(
                weightKg = store.getDouble(PreferenceKeys.weight, defaults.weightKg),
                gender = Gender.safeValueOf(genderStr) ?: defaults.gender,
                hasAgreedDisclosure = store.getBoolean(
                    PreferenceKeys.hasAgreedDisclosure,
                    defaults.hasAgreedDisclosure
                ),
                startOfDay = LocalTime.fromPrefsString(startOfDayStr) ?: defaults.startOfDay,
                alchoholGramsInUnit = store.getDouble(
                    PreferenceKeys.alchoholGramsInUnit,
                    defaults.alchoholGramsInUnit
                ),
                locale = AppLocale.forName(localeStr),
                theme = ThemeSelection.forName(themeStr),
                dynamicPalette = store.getBoolean(
                    PreferenceKeys.dynamicPalette,
                    defaults.dynamicPalette
                ),
            )
        }
    }
}

fun PreferenceStore.getDouble(key: String, default: Double): Double {
    val str = getString(key, default.toString())
    return safeToDouble(str) ?: default
}

fun PreferenceStore.getBoolean(key: String, default: Boolean): Boolean {
    val str = getString(key, default.toString())
    getLogger("us").info("Loaded bool $key: $str")
    return str.lowercase() == "true"
}

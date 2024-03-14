package fi.tuska.beerclock.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.localization.AppLocale
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.theme.ThemeSelection
import kotlinx.datetime.LocalTime

data class UserPreferences(
    /**
     * App locale, if selected from settings (null means follow system locale).
     */
    val locale: AppLocale? = null,
    /**
     * App theme selection
     */
    val theme: ThemeSelection = ThemeSelection.SYSTEM,
    /**
     * Use dynamic color palette (if available). The dynamic palette works on Android,
     * and it's generated based on the user's background image colors.
     */
    val dynamicPalette: Boolean = true,
    /** User gender. Affects BAC calculation formula. */
    val gender: Gender = Gender.MALE,
    /** User weight, in kilograms. Affects BAC calculation formula. */
    val weightKg: Double = 70.0,
    /**
     * When the "drinking day" starts. Drinks consumed before this time will be shown
     * on the previous day's listing.
     */
    val startOfDay: LocalTime = LocalTime(4, 0),
    /**
     * How many grams of alcohol is there in a single standard unit?
     * 12.0 grams of alcohol per unit is the default for Finland (and various other countries).
     */
    val alchoholGramsInUnit: Double = 12.0,
    /**
     * What is the limit of blood alcohol concentration (per mille) for driving?
     */
    val drivingLimitBac: Double = 0.5,
    /**
     * What is the maximum number of units the app gauges show on the daily status view?
     */
    val maxDailyUnits: Double = 7.0,
    /**
     * What is the maximum number of units the app gauges show on the weekly status view?
     */
    val maxWeeklyUnits: Double = 14.0,
    /**
     * What is the maximum BAC per mille value the app gauges show on the daily status view?
     */
    val maxBAC: Double = 1.0,
) {
    /**
     * Volume of distribution, approximated from body weight and gender multiplier.
     */
    val volumeOfDistribution =
        BacFormulas.volumeOfDistribution(weightKg, gender.volumeOfDistributionMultiplier)

    /**
     * Estimate of the rate of alcohol games burned per hour (g/h).
     */
    val alcoholBurnOffRate = BacFormulas.alcoholBurnOffRate(volumeOfDistribution)

    /**
     * This is a multiplier `m` such that the formula `abv * quantityLiters * m`
     * gives the number of standards units, for the given user preferences.
     * To be used in SQL queries, units are the same that are used in DB.
     */
    val alcoholAbvLitersToUnitMultiplier =
        BacFormulas.getUnitsFromDisplayQuantityAbv(1.0, 1.0, this) * 10_000
}

private val logger = getLogger("UserPreferences")

/** Injected by Koin. Created at app startup and updated whenever settings are updated. */
class GlobalUserPreferences(state: UserPreferences) {
    var prefs: UserPreferences by mutableStateOf(state)
        private set

    init {
        logger.info("Initialized: $prefs")
    }

    fun update(state: UserPreferences) {
        this.prefs = state
        logger.debug("Updated: $prefs")
    }
}

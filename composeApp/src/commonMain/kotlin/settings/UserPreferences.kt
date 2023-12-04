package fi.tuska.beerclock.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.localization.AppLocale
import fi.tuska.beerclock.logging.getLogger
import kotlinx.datetime.LocalTime

data class UserPreferences(
    /** User gender. Affects BAC calculation formula. */
    val gender: Gender = Gender.MALE,
    /** User weight, in kilograms. Affects BAC calculation formula. */
    val weightKg: Double = 70.0,
    /**
     * When the "drinking day" starts. Drinks consumed before this time will be shown
     * on the previous day's listing.
     */
    val startOfDay: LocalTime = LocalTime(6, 0),
    /**
     * How many grams of alcohol is there in a single standard unit?
     * 12.0 grams of alcohol per unit is the default for Finland (and various other countries).
     */
    val alchoholGramsInUnit: Double = 12.0,
    /**
     * App locale, if selected from settings (null means follow system locale).
     */
    val locale: AppLocale? = null,
) {
    /**
     * Volume of distribution, approximated from body weight and gender multiplier.
     * See [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     *
     * Vd is the volume of distribution (L);
     * typically body weight (kg) multiplied by 0.71 L/kg for men and 0.58 L/kg for women
     */
    val volumeOfDistribution = gender.volumeOfDistributionMultiplier * weightKg


    /**
     * Estimate of the rate of alcohol games burned per hour.
     *
     * From [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     * β is the rate at which alcohol is eliminated (g/L/hr); typically 0.15
     */
    val alcoholBurnOffRate = 0.15 * volumeOfDistribution

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

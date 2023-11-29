package fi.tuska.beerclock.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.util.fromPrefsString
import fi.tuska.beerclock.util.safeToDouble
import fi.tuska.beerclock.util.toPrefsString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime

internal class UserStore {

    var state: UserStore by mutableStateOf(UserStore())
        private set

    init {
        this.loadFromPrefs()
    }

    suspend fun setGender(gender: Gender) {
        if (state.gender == gender) {
            return
        }
        withContext(Dispatchers.IO) {
            setState { copy(gender = gender) }
            val prefs = PreferenceProvider.getPrefs()
            prefs.setString(PreferenceKeys.gender, gender.toString())
        }
    }

    /**
     * @param weight weight, in kilograms
     */
    suspend fun setWeight(weight: Double) {
        if (state.weightKg == weight) {
            return
        }
        withContext(Dispatchers.IO) {
            setState { copy(weightKg = weight) }
            val prefs = PreferenceProvider.getPrefs()
            prefs.setString(PreferenceKeys.weight, weight.toString())
        }
    }

    /**
     * @param startOfDay the time when day starts
     */
    suspend fun setStartOfDay(startOfDay: LocalTime) {
        if (state.startOfDay == startOfDay) {
            return
        }
        withContext(Dispatchers.IO) {
            setState { copy(startOfDay = startOfDay) }
            val prefs = PreferenceProvider.getPrefs()
            prefs.setString(PreferenceKeys.startOfDay, startOfDay.toPrefsString())
        }
    }

    private fun loadFromPrefs() {
        val prefs = PreferenceProvider.getPrefs()
        val weightStr = prefs.getString(PreferenceKeys.weight, state.weightKg.toString())
        val genderStr = prefs.getString(PreferenceKeys.gender, state.gender.toString())
        val startOfDayStr =
            prefs.getString(PreferenceKeys.startOfDay, state.startOfDay.toPrefsString())

        setState {
            copy(
                weightKg = safeToDouble(weightStr) ?: state.weightKg,
                gender = Gender.safeValueOf(genderStr) ?: state.gender,
                startOfDay = LocalTime.fromPrefsString(startOfDayStr) ?: state.startOfDay
            )
        }
    }

    private inline fun setState(update: UserStore.() -> UserStore) {
        state = state.update()
    }

    data class UserStore(
        /** User gender. Affects BAC calculation formula. */
        val gender: Gender = Gender.MALE,
        /** User weight, in kilograms. Affects BAC calculation formula. */
        val weightKg: Double = 70.0,
        /**
         * When the "drinking day" starts. Drinks consumed before this time will be shown
         * on the previous day's listing.
         */
        val startOfDay: LocalTime = LocalTime(6, 0)
    )

    object PreferenceKeys {
        const val weight = "prefs.user.weight"
        const val gender = "prefs.user.gender"
        const val startOfDay = "prefs.user.startOfDay"
    }

}

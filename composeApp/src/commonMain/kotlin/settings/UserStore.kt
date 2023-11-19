package fi.tuska.beerclock.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.util.safeToDouble

internal class UserStore {

    var state: UserStore by mutableStateOf(UserStore())
        private set

    init {
        this.loadFromPrefs()
    }

    fun setGender(gender: Gender) {
        if (state.gender == gender) {
            return
        }
        setState { copy(gender = gender) }
        val prefs = PreferenceProvider.getPrefs()
        prefs.setString(PreferenceKeys.gender, gender.toString())
    }

    /**
     * @param weight weight, in kilograms
     */
    fun setWeight(weight: Double) {
        if (state.weightKg == weight) {
            return
        }
        setState { copy(weightKg = weight) }
        val prefs = PreferenceProvider.getPrefs()
        prefs.setString(PreferenceKeys.weight, weight.toString())
    }

    private fun loadFromPrefs() {
        val prefs = PreferenceProvider.getPrefs()
        val weightStr = prefs.getString(PreferenceKeys.weight, state.weightKg.toString())
        val genderStr = prefs.getString(PreferenceKeys.gender, state.gender.toString())
        setState {
            copy(
                weightKg = safeToDouble(weightStr) ?: state.weightKg,
                gender = Gender.safeValueOf(genderStr) ?: state.gender
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
    )

    object PreferenceKeys {
        const val weight = "prefs.user.weight"
        const val gender = "prefs.user.gender"
    }

}

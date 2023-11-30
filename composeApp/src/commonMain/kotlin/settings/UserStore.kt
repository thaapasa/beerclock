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
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class UserStore : KoinComponent {
    private val store: PreferenceStore = get()

    var state: UserStore by mutableStateOf(UserStore())
        private set

    suspend fun setGender(gender: Gender) {
        if (state.gender == gender) {
            return
        }
        setState { copy(gender = gender) }
        setStateValue(PreferenceKeys.gender, gender.toString())
    }

    suspend fun setWeight(weight: Double) {
        if (state.weightKg == weight) {
            return
        }
        setState { copy(weightKg = weight) }
        setStateValue(PreferenceKeys.weight, weight.toString())
    }

    suspend fun setStartOfDay(startOfDay: LocalTime) {
        if (state.startOfDay == startOfDay) {
            return
        }
        setState { copy(startOfDay = startOfDay) }
        setStateValue(PreferenceKeys.startOfDay, startOfDay.toPrefsString())
    }

    suspend fun setAlcoholGramsInUnit(gramsInUnit: Double) {
        if (state.alchoholGramsInUnit == gramsInUnit) {
            return
        }
        setState { copy(alchoholGramsInUnit = gramsInUnit) }
        setStateValue(PreferenceKeys.alchoholGramsInUnit, gramsInUnit.toString())
    }

    private suspend fun setStateValue(stateKey: String, stringified: String) {
        withContext(Dispatchers.IO) {
            store.setString(stateKey, stringified)
        }
    }

    fun load() {
        val weightStr = store.getString(PreferenceKeys.weight, state.weightKg.toString())
        val genderStr = store.getString(PreferenceKeys.gender, state.gender.toString())
        val startOfDayStr =
            store.getString(PreferenceKeys.startOfDay, state.startOfDay.toPrefsString())
        val alcoholGramsInUnitStr =
            store.getString(
                PreferenceKeys.alchoholGramsInUnit,
                state.alchoholGramsInUnit.toString()
            )

        setState {
            copy(
                weightKg = safeToDouble(weightStr) ?: state.weightKg,
                gender = Gender.safeValueOf(genderStr) ?: state.gender,
                startOfDay = LocalTime.fromPrefsString(startOfDayStr) ?: state.startOfDay,
                alchoholGramsInUnit = safeToDouble(alcoholGramsInUnitStr)
                    ?: state.alchoholGramsInUnit
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
        val startOfDay: LocalTime = LocalTime(6, 0),
        /**
         * How many grams of alcohol is there in a single standard unit?
         * 12.0 grams of alcohol per unit is the default for Finland (and various other countries).
         */
        val alchoholGramsInUnit: Double = 12.0
    )

    object PreferenceKeys {
        const val weight = "prefs.user.weight"
        const val gender = "prefs.user.gender"
        const val startOfDay = "prefs.user.startOfDay"
        const val alchoholGramsInUnit = "prefs.user.alchoholGramsInUnit"
    }

}

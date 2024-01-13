package fi.tuska.beerclock.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class SettingsViewModel : ViewModel(), KoinComponent {

    // Initialize editors based on the global state
    private val prefs: GlobalUserPreferences = get()

    // Push updated to user prefs using the UserStore. The updated will be automatically
    // reflected in the global user prefs state as well.
    private val store = UserStore()

    var settingsTab by mutableStateOf(SettingsTabs.USER)

    var locale by mutableStateOf(prefs.prefs.locale)
    var weightKg by mutableStateOf(prefs.prefs.weightKg)
    var gender by mutableStateOf(prefs.prefs.gender)
    var startOfDay by mutableStateOf(prefs.prefs.startOfDay)
    var gramsInUnit by mutableStateOf(prefs.prefs.alchoholGramsInUnit)
    var drivingLimitBac by mutableStateOf(prefs.prefs.drivingLimitBac)

    fun saveWeight() = launch { store.setWeight(weightKg) }
    fun saveGender() = launch { store.setGender(gender) }
    fun saveStartOfDay() = launch { store.setStartOfDay(startOfDay) }
    fun saveGramsInUnitText() = launch { store.setAlcoholGramsInUnit(gramsInUnit) }
    fun saveLocale() = launch { store.setLocale(locale) }
    fun saveDrivingLimitBac() = launch { store.setDrivingLimitBac(drivingLimitBac) }

    @Composable
    fun trackChanges() {
        LaunchedEffect(locale) { saveLocale() }
        LaunchedEffect(weightKg) { saveWeight() }
        LaunchedEffect(gender) { saveGender() }
        LaunchedEffect(startOfDay) { saveStartOfDay() }
        LaunchedEffect(gramsInUnit) { saveGramsInUnitText() }
        LaunchedEffect(drivingLimitBac) { saveDrivingLimitBac() }
    }

}

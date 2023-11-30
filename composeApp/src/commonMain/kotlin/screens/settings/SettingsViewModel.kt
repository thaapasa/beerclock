package fi.tuska.beerclock.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.Gender
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.util.ZeroHour
import kotlinx.coroutines.launch

private val logger = getLogger("SettingsScreen")

internal class SettingsViewModel : ViewModel() {

    private val prefs = UserStore()

    var weightKg by mutableStateOf(0.0)
    var gender by mutableStateOf(Gender.FEMALE)
    var startOfDay by mutableStateOf(ZeroHour)
    var gramsInUnit by mutableStateOf(0.0)

    fun loadFromPrefs() = launch {
        prefs.load()
        logger.info("Loaded prefs: ${prefs.state}")
        weightKg = prefs.state.weightKg
        gender = prefs.state.gender
        startOfDay = prefs.state.startOfDay
        gramsInUnit = prefs.state.alchoholGramsInUnit
    }

    fun saveWeight() = launch { prefs.setWeight(weightKg) }
    fun saveGender() = launch { prefs.setGender(gender) }
    fun saveStartOfDay() = launch { prefs.setStartOfDay(startOfDay) }
    fun saveGramsInUnitText() = launch { prefs.setAlcoholGramsInUnit(gramsInUnit) }

}

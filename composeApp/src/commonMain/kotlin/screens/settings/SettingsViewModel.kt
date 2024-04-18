package fi.tuska.beerclock.screens.settings

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.backup.isDataImportExportSupported
import fi.tuska.beerclock.backup.isJAlcoMeterImportSupported
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.theme.supportsDynamicTheme
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class SettingsViewModel(val snackbar: SnackbarHostState) : ViewModel(), KoinComponent {

    // Initialize editors based on the global state
    private val prefs: GlobalUserPreferences = get()

    // Push updated to user prefs using the UserStore. The updated will be automatically
    // reflected in the global user prefs state as well.
    private val store = UserStore()

    val dynamicThemeSupported = supportsDynamicTheme()

    var settingsTab by mutableStateOf(SettingsTabs.USER)

    var locale by mutableStateOf(prefs.prefs.locale)
    var theme by mutableStateOf(prefs.prefs.theme)
    var dynamicPalette by mutableStateOf(prefs.prefs.dynamicPalette)
    var weightKg by mutableStateOf(prefs.prefs.weightKg)
    var gender by mutableStateOf(prefs.prefs.gender)
    var startOfDay by mutableStateOf(prefs.prefs.startOfDay)
    var gramsInUnit by mutableStateOf(prefs.prefs.alchoholGramsInUnit)
    var drivingLimitBac by mutableStateOf(prefs.prefs.drivingLimitBac)
    var maxBac by mutableStateOf(prefs.prefs.maxBac)
    var maxDailyUnits by mutableStateOf(prefs.prefs.maxDailyUnits)
    var maxWeeklyUnits by mutableStateOf(prefs.prefs.maxWeeklyUnits)

    fun volumeOfDistribution() = prefs.prefs.volumeOfDistribution
    fun alcoholBurnOffRate() = prefs.prefs.alcoholBurnOffRate

    fun saveWeight() = launch { store.setWeight(weightKg) }
    fun saveGender() = launch { store.setGender(gender) }
    fun saveStartOfDay() = launch { store.setStartOfDay(startOfDay) }
    fun saveGramsInUnitText() = launch { store.setAlcoholGramsInUnit(gramsInUnit) }
    fun saveLocale() = launch { store.setLocale(locale) }
    fun saveTheme() = launch { store.setTheme(theme) }
    fun saveDynamicPalette() = launch { store.setDynamicPalette(dynamicPalette) }
    fun saveDrivingLimitBac() = launch { store.setDrivingLimitBac(drivingLimitBac) }
    fun saveMaxBAC() = launch { store.setMAXBac(maxBac) }
    fun saveMaxDailyUnits() = launch { store.setMaxDailyUnits(maxDailyUnits) }
    fun saveMaxWeeklyUnits() = launch { store.setMaxWeeklyUnits(maxWeeklyUnits) }

    @Composable
    fun trackChanges() {
        LaunchedEffect(locale) { saveLocale() }
        LaunchedEffect(theme) { saveTheme() }
        LaunchedEffect(dynamicPalette) { saveDynamicPalette() }
        LaunchedEffect(weightKg) { saveWeight() }
        LaunchedEffect(gender) { saveGender() }
        LaunchedEffect(startOfDay) { saveStartOfDay() }
        LaunchedEffect(gramsInUnit) { saveGramsInUnitText() }
        LaunchedEffect(drivingLimitBac) { saveDrivingLimitBac() }
        LaunchedEffect(maxBac) { saveMaxBAC() }
        LaunchedEffect(maxDailyUnits) { saveMaxDailyUnits() }
        LaunchedEffect(maxWeeklyUnits) { saveMaxWeeklyUnits() }
    }

    val canImportExportDb by lazy { isDataImportExportSupported() }
    val canImportFromJAlcoMeter by lazy { isJAlcoMeterImportSupported() }
    val showImportTab by lazy { canImportExportDb || canImportFromJAlcoMeter }

}

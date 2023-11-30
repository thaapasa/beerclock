package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.Gender
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.TimeInputField
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.ZeroHour
import kotlinx.coroutines.launch

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> SettingsPage(innerPadding) },
            title = strings.settings.title
        )
    }
}

@Composable
fun SettingsPage(innerPadding: PaddingValues) {

    val vm = rememberWithDispose { SettingsScreenViewModel() }

    LaunchedEffect(Unit) {
        vm.loadFromPrefs()
    }

    LaunchedEffect(vm.weightKg) { vm.saveWeight() }
    LaunchedEffect(vm.gender) { vm.saveGender() }
    LaunchedEffect(vm.startOfDay) { vm.saveStartOfDay() }
    LaunchedEffect(vm.gramsInUnit) { vm.saveGramsInUnitText() }

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth()
    ) {
        DecimalField(
            value = vm.weightKg,
            onValueChange = { vm.weightKg = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = strings.settings.weightLabel) },
            trailingIcon = { Text(strings.settings.weightUnit) },
            supportingText = { Text(strings.settings.weightDescription) }
        )
        GenderSelector(
            selected = vm.gender,
            onSelect = { vm.gender = it },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            supportingText = { Text(strings.settings.genderDescription) }
        )
        Spacer(Modifier.height(16.dp))
        TimeInputField(
            value = vm.startOfDay,
            onValueChange = { vm.startOfDay = it },
            modifier = Modifier.fillMaxWidth(),
            labelText = strings.settings.startOfDay,
            supportingText = { Text(strings.settings.startOfDayDescription) },
        )
        Spacer(Modifier.height(16.dp))
        DecimalField(
            value = vm.gramsInUnit,
            onValueChange = { vm.gramsInUnit = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(strings.settings.alcoholGramsLabel) },
            supportingText = { Text(strings.settings.alcoholGramsDescription) },
            trailingIcon = {
                Text(
                    strings.settings.alcoholGramsUnit,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        AlcoholUnitsDropdown(onSelect = { vm.gramsInUnit = it })
    }
}

private val logger = getLogger("SettingsScreen")

internal class SettingsScreenViewModel() : ViewModel() {

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

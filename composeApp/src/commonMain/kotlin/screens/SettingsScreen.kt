package fi.tuska.beerclock.common.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.common.localization.strings
import fi.tuska.beerclock.common.settings.UserStore
import fi.tuska.beerclock.common.ui.GenderSelector
import fi.tuska.beerclock.common.ui.SubLayout
import fi.tuska.beerclock.common.util.safeToDouble

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(content = { SettingsPage() }, title = strings.settings.title)
    }
}

@Composable
fun SettingsPage() {

    val userPrefs = remember { UserStore() }

    var weightText by remember { mutableStateOf(userPrefs.state.weightKg.toString()) }
    var gender by remember { mutableStateOf(userPrefs.state.gender) }

    LaunchedEffect(weightText) {
        val weightVal = safeToDouble(weightText)
        if (weightVal != null) {
            userPrefs.setWeight(weightVal)
        }
    }

    LaunchedEffect(gender) {
        userPrefs.setGender(gender)
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = weightText,
            onValueChange = { weightText = it },
            label = { Text(text = strings.settings.weightLabel) })
        GenderSelector(selectedValue = gender, onSelectGender = { gender = it })
    }
}
package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.safeToDouble

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

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth()
    ) {
        TextField(value = weightText,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { weightText = it },
            label = { Text(text = strings.settings.weightLabel) })
        GenderSelector(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            selected = gender,
            onSelect = { gender = it })
    }
}
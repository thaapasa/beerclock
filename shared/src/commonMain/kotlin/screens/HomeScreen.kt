package fi.tuska.beerclock.common.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.common.ui.MainLayout

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        MainLayout(content = {
            Text("Appiksen päänäkymä. Tähän laitetaan tieto tämän päivän tilanteesta")
        })
    }
}

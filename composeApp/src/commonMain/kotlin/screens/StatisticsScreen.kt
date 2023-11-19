package fi.tuska.beerclock.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.ui.SubLayout

object StatisticsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(content = { Text("Tilastot") }, title = "Tilastot")
    }
}

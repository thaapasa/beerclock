package fi.tuska.beerclock.screens.disclosure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.images.AppImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.settings.UserStore
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object DisclosureScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> DisclosurePage(innerPadding) },
            title = "",
            showTopBar = false,
        )
    }
}

@Composable
fun DisclosurePage(innerPadding: PaddingValues) {
    val strings = Strings.get()
    val scrollState = rememberScrollState()
    val navigator = LocalNavigator.currentOrThrow
    val vm = rememberWithDispose { DisclosureViewModel(navigator) }

    Column(
        Modifier.padding(innerPadding).fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = AppImage.BEERCLOCK_FEATURE.painter(),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Feature"
        )
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = strings.appName,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            strings.disclosureTexts.map {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = vm::agreeToDisclosure) {
                Text(
                    strings.dismissDisclosure,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

class DisclosureViewModel(private val navigator: Navigator) : ViewModel(), KoinComponent {
    val prefs: GlobalUserPreferences = get()
    private val store = UserStore()

    fun agreeToDisclosure() {
        launch {
            store.setHasAgreedDisclosure(true)
        }
        navigator.replaceAll(HomeScreen())
    }
}

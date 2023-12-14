package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.LatestDrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.drinks.create.AddDrinkDialog
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import org.koin.core.component.KoinComponent


object NewDrinkSearchScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val strings = Strings.get()
        val vm = rememberWithDispose { NewDrinkViewModel() }
        val navigator = LocalNavigator.currentOrThrow

        MainLayout(showTopBar = false) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize()) {
                SearchBar(
                    vm.query,
                    onQueryChange = { vm.query = it },
                    placeholder = { Text(strings.newdrink.searchPlaceholder) },
                    active = vm.active,
                    onActiveChange = { vm.active = it },
                    onSearch = {},
                    leadingIcon = { AppIcon.SEARCH.icon() },
                    modifier = Modifier.fillMaxWidth()
                ) {}

                if (!vm.active) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LatestDrinks(vm::addDrink)
                }
            }
            if (vm.dialogOpen) {
                AddDrinkDialog(
                    proto = vm.proto,
                    onDrinksUpdated = { navigator.replaceAll(HomeScreen) },
                    onClose = vm::closeDialog
                )
            }
        }
    }
}

class NewDrinkViewModel : ViewModel(), KoinComponent {
    var query by mutableStateOf("")
    var active by mutableStateOf(false)
    var dialogOpen by mutableStateOf(false)
        private set
    var proto: BasicDrinkInfo? = null

    fun addDrink(latest: LatestDrinkInfo?) {
        if (dialogOpen) {
            return
        }
        proto = latest
        dialogOpen = true
    }

    fun closeDialog() {
        dialogOpen = false
    }
}

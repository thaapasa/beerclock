package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.SwipeControl
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import fi.tuska.beerclock.util.JavaSerializable
import kotlinx.datetime.LocalDate

data class NewDrinkSearchScreen(
    /**
     * Suggested drinking date for new drinks (given from history screen
     * when recording drinks to past dates).
     */
    val date: LocalDate? = null,
    /**
     * Initial search string. Updated from the view model when navigating to edit screen
     * so that the query is restored when returning from editor.
     */
    var searchString: String? = null,
) : Screen, JavaSerializable {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val strings = Strings.get()
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose {
            NewDrinkViewModel(navigator, date, searchString) {
                searchString = it
            }
        }
        val searchResults by vm.searchResults.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current

        val query = vm.searchQuery

        MainLayout(showTopBar = false, snackbarHostState = vm.snackbar) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                SearchBar(
                    query,
                    onQueryChange = { vm.searchQuery = it },
                    placeholder = { Text(strings.newdrink.searchPlaceholder) },
                    active = vm.active,
                    onActiveChange = vm::toggleActive,
                    onSearch = { },
                    leadingIcon = { IconButton(onClick = { keyboardController?.hide() }) { AppIcon.SEARCH.icon() } },
                    trailingIcon = {
                        IconButton(onClick = {
                            vm.searchQuery = ""
                            keyboardController?.hide()
                        }) { AppIcon.CLOSE.icon() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            TextListItem(drink = vm.newDrinkHeaderInfo())
                        }
                        items(searchResults.drinks, key = { it.key }) {
                            if (it is DrinkInfo) {
                                SwipeControl(
                                    onDelete = { vm.deleteDrinkInfo(it) },
                                    onModify = { vm.modifyDrinkInfo(it) },
                                ) {
                                    BasicDrinkItem(
                                        drink = it,
                                        onClick = vm::selectDrink,
                                        onLongClick = vm::editDrink,
                                    )
                                }
                            } else {
                                BasicDrinkItem(
                                    drink = it,
                                    onClick = vm::selectDrink,
                                    onLongClick = vm::editDrink,
                                )
                            }
                        }
                        if (searchResults.showEmptyWarning) {
                            item {
                                TextListItem(vm.emptyListWarningInfo())
                            }
                        }
                    }
                }
            }
            vm.DrinkInfoDialog()
        }
    }
}

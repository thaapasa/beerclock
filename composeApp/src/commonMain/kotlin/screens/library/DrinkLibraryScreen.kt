package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.newdrink.BasicDrinkItem
import fi.tuska.beerclock.screens.newdrink.TextDrinkInfo
import fi.tuska.beerclock.ui.composables.SwipeControl
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.JavaSerializable

data class DrinkLibraryScreen(var initialCategory: Category?) : Screen, JavaSerializable {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose {
            DrinkLibraryViewModel(
                navigator,
                initialCategory,
                { initialCategory = it })
        }
        val strings = Strings.get()
        SubLayout(
            title = strings.library.title,
            actions = {
                IconButton(onClick = vm::addNewDrink) {
                    AppIcon.ADD_CIRCLE.icon(tint = MaterialTheme.colorScheme.primary)
                }
            },
            snackbarHostState = vm.snackbar
        ) { innerPadding -> DrinkLibraryPage(innerPadding, vm) }
    }
}


@Composable
fun DrinkLibraryPage(innerPadding: PaddingValues, vm: DrinkLibraryViewModel) {
    val searchResults by vm.libraryResults.collectAsState()
    val state = rememberLazyListState(0)

    vm.ScrollToEventItem(searchResults, state)

    Column(
        Modifier.padding(innerPadding).padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        CategoryBar(selected = vm.selectedCategory, select = vm::selectCategory)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
                .clip(RoundedCornerShape(12.dp)), state = state
        ) {
            item { BasicDrinkItem(drink = vm.categoryHeaderInfo()) }
            items(searchResults, key = { it.key }) { drink ->
                SwipeControl(
                    onModify = { vm.editDrink(drink) },
                    onDelete = { vm.deleteDrink(drink) }) {
                    BasicDrinkItem(
                        drink = drink,
                        onClick = { vm.viewDrink(drink) })
                }
            }
            item {
                BasicDrinkItem(
                    drink = TextDrinkInfo(
                        key = "default-drinks",
                        name = Strings.get().library.addDefaultDrinks,
                        icon = AppIcon.DRINK,
                        onClick = { vm.addExampleDrinks() },
                    ), onClick = { vm.addExampleDrinks() }
                )
            }
        }
        vm.InfoDialog()
    }

}

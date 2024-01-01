package fi.tuska.beerclock.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.screens.newdrink.BasicDrinkItem
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout

object DrinkLibraryScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> DrinkLibraryPage(innerPadding) },
            title = "Juomakirjasto"
        )
    }
}


@Composable
fun DrinkLibraryPage(innerPadding: PaddingValues) {
    val vm = rememberWithDispose { DrinkLibraryViewModel() }
    val searchResults by vm.libraryResults.collectAsState()
    Column(
        Modifier.padding(innerPadding).padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        CategoryBar(selected = vm.selectedCategories(), toggle = vm::toggleCategory)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
                .clip(RoundedCornerShape(12.dp))
        ) {
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].key },
                itemContent = { index ->
                    val drink = searchResults[index]
                    BasicDrinkItem(drink = drink)
                }
            )
        }
    }

}

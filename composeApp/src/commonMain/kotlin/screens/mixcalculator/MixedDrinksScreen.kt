package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.newdrink.BasicDrinkItem
import fi.tuska.beerclock.ui.composables.SwipeControl
import fi.tuska.beerclock.ui.layout.SubLayout
import fi.tuska.beerclock.util.CommonParcelize

@CommonParcelize
object MixedDrinksScreen : ParcelableScreen {

    val vm = MixedDrinksViewModel()

    @Composable
    override fun Content() {
        val strings = Strings.get()
        SubLayout(
            title = strings.mixedDrinks.title,
            actions = {
                IconButton(onClick = vm::addNewMix) {
                    AppIcon.ADD_CIRCLE.icon(tint = MaterialTheme.colorScheme.primary)
                }
            },
            snackbarHostState = vm.snackbar
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) { MixedDrinksView(vm) }
        }
    }
}

@Composable
fun ColumnScope.MixedDrinksView(vm: MixedDrinksViewModel) {
    vm.EditorDialog()

    val searchResults by vm.mixedDrinkResults.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f)
            .clip(RoundedCornerShape(12.dp))
    ) {
        items(searchResults, key = { it.key }) { mix ->
            SwipeControl(
                onModify = { vm.modifyMix(mix) },
                onDelete = { vm.deleteMix(mix) }) {
                BasicDrinkItem(
                    drink = mix.asDrinkInfo(),
                    onClick = { vm.modifyMix(mix) })
            }
        }
    }
}

package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
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
fun MixedDrinksView(vm: MixedDrinksViewModel) {
    vm.EditorDialog()
    Text("TODO: Existing drink mixes shown here")
}

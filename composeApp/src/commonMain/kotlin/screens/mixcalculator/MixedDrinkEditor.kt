package fi.tuska.beerclock.screens.mixcalculator

import MixedDrinkItemListItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinkItem
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.editor.DrinkImageSelectField
import fi.tuska.beerclock.ui.components.AppDialog
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch

private val logger = getLogger("MixedDrinkEditor")
private val gap = 16.dp

@Composable
fun ColumnScope.MixedDrinkEditor(vm: MixedDrinkEditorViewModel, onClose: () -> Unit) {
    val strings = Strings.get()

    Row(Modifier.fillMaxWidth().padding(horizontal = gap)) {
        OutlinedTextField(
            label = { Text(strings.drinkDialog.nameLabel) },
            value = vm.name,
            singleLine = true,
            onValueChange = { vm.name = it },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(gap))
        DrinkImageSelectField(
            value = vm.image,
            onValueChange = { vm.image = it },
            titleText = strings.drinkDialog.selectImageTitle,
        )
    }
    Spacer(modifier = Modifier.height(gap))
    OutlinedTextField(
        label = { Text("Ohjeet") },
        value = vm.instructions,
        singleLine = false,
        onValueChange = { vm.instructions = it },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    )
    Spacer(modifier = Modifier.height(gap))
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Row(
        Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            .height(64.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("Ainekset")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = vm::addNew) {
            AppIcon.ADD_CIRCLE.icon(tint = MaterialTheme.colorScheme.primary)
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        vm.items.map {
            MixedDrinkItemListItem(it, onModify = vm::editItem, onDelete = vm::deleteItem)
        }
    }
    vm.itemEditor?.let {
        AppDialog(onClose = vm::dismissEditor) {
            MixedDrinkItemEditor(it, onClose = vm::dismissEditor)
        }
    }
}

class MixedDrinkEditorViewModel(proto: MixedDrink) : ViewModel() {
    val id = proto.id
    var name by mutableStateOf(proto.info.name)
    var instructions by mutableStateOf(proto.info.instructions ?: "")
    var image by mutableStateOf(proto.info.image)
    var category by mutableStateOf(proto.info.category)
    var items = mutableStateListOf<MixedDrinkItem>()
    val mixService = MixedDrinksService()
    var itemEditor by mutableStateOf<MixedDrinkItemEditorViewModel?>(null)
        private set

    init {
        items.addAll(proto.items)
    }

    fun toMixedDrink(): MixedDrink =
        MixedDrink(
            info = MixedDrinkInfo(
                id = id,
                name = name,
                instructions = instructions.ifBlank { null },
                image = image,
                category = category
            ),
            items = items
        )

    override fun onDispose() {
        this.itemEditor?.onDispose()
    }

    fun addNew() {
        logger.info("Adding new item")
        this.itemEditor?.onDispose()
        this.itemEditor = MixedDrinkItemEditorViewModel(proto = null, saveAction = {
            this.items.add(it)
            this.dismissEditor()
        })
    }

    fun editItem(item: MixedDrinkItem) {
        logger.info("Editing drink item $item")
        this.itemEditor?.onDispose()
        this.itemEditor = MixedDrinkItemEditorViewModel(proto = item, saveAction = {
            val index = this.items.indexOf(item)
            if (index >= 0) {
                this.items.removeAt(index)
                this.items.add(index, it)
            } else {
                this.items.add(it)
            }
            this.dismissEditor()
        }, deleteAction = {
            this.items.remove(item)
            this.dismissEditor()
        })
    }

    fun deleteItem(item: MixedDrinkItem) {
        this.items.remove(item)
    }

    fun dismissEditor() {
        this.itemEditor = null
    }

    fun save(andThen: () -> Unit) {
        launch {
            if (id != null) {
                mixService.updateDrinkMix(id, toMixedDrink())
            } else {
                mixService.insertDrinkMix(toMixedDrink())
            }
            andThen()
        }
    }

}

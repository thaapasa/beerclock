package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun <T> ImageSelectField(
    value: T,
    options: List<T>,
    titleText: String? = null,
    onValueChange: (image: T) -> Unit,
    valueToImage: @Composable (value: T, modifier: Modifier) -> Unit,
    minImageSize: Dp
) {
    var dialogOpen by remember { mutableStateOf(false) }
    valueToImage.invoke(value, modifier = Modifier.clickable { dialogOpen = !dialogOpen })
    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    titleText?.let {
                        Text(
                            titleText,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            thickness = 1.dp,
                            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = minImageSize),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(options) {
                            valueToImage.invoke(it, Modifier.clickable {
                                onValueChange(it)
                                dialogOpen = false
                            })
                        }
                    }
                }
            }
        }
    }
}
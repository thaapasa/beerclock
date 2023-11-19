package fi.tuska.beerclock.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import fi.tuska.beerclock.localization.strings
import kotlinx.coroutines.launch

@Composable
fun ToggleDrawerButton(drawerState: DrawerState, icon: ImageVector = Icons.Default.Menu) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(onClick = {
        coroutineScope.launch {
            if (drawerState.isOpen) {
                drawerState.close()
            } else {
                drawerState.open()
            }
        }
    }) {
        Icon(
            imageVector = icon,
            contentDescription = strings.menu.main,
        )
    }
}

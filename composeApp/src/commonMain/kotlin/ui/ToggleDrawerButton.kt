package fi.tuska.beerclock.common.ui

import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import fi.tuska.beerclock.common.localization.strings
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

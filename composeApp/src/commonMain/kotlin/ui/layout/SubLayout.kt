package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubLayout(
    title: String,
    showTopBar: Boolean = true,
    snackbarHostState: SnackbarHostState? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val strings = Strings.get()
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar =
            {
                if (showTopBar) {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        actions = actions,
                        title = { Text(title) },
                        navigationIcon = {
                            IconButton({ navigator.pop() }) {
                                Icon(
                                    painter = AppIcon.ARROW_BACK.painter(),
                                    contentDescription = strings.menu.goBack,
                                )
                            }
                        })
                }
            },
        content = content
    )
}

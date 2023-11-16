package fi.tuska.beerclock.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.common.localization.strings

@Composable
fun SubLayout(content: @Composable (PaddingValues) -> Unit, title: String) {
    val scaffoldState = rememberScaffoldState()
    val navigator = LocalNavigator.currentOrThrow
    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState, topBar = {
                TopAppBar(title = { Text(title) },
                    navigationIcon = {
                        IconButton({ navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = strings.menu.main,
                            )
                        }
                    })
            }, content = content
        )
    }
}

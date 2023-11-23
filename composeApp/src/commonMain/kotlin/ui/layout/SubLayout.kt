package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubLayout(content: @Composable (PaddingValues) -> Unit, title: String) {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
                title = { Text(title) },
                navigationIcon = {
                    IconButton({ navigator.pop() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = strings.menu.goBack,
                        )
                    }
                })
        }, content = content
    )
}

package fi.tuska.beerclock.screens.drinks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DialogHeader
import fi.tuska.beerclock.ui.layout.SubLayout

@Composable
fun DrinkDialogLayout(
    title: String,
    saveButton: (@Composable (modifier: Modifier) -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val navigator = LocalNavigator.currentOrThrow
    val scrollState = rememberScrollState()
    val strings = Strings.get()
    SubLayout(title = title, showTopBar = false) {
        Column(modifier = Modifier.fillMaxSize()) {
            DialogHeader(
                titleText = title,
                leadingIcon = { modifier ->
                    AppIcon.CLOSE.iconButton(
                        onClick = navigator::pop,
                        modifier = modifier,
                        contentDescription = strings.dialogClose
                    )
                },
                trailingIcon = saveButton
            )
            Column(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                content()
            }
        }
    }
}

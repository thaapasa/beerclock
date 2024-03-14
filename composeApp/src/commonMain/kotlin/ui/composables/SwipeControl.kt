package fi.tuska.beerclock.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeControl(
    onDelete: () -> Unit,
    onModify: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    var clicks by mutableStateOf(0)
    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
        when (it) {
            SwipeToDismissBoxValue.StartToEnd -> onModify()
            SwipeToDismissBoxValue.EndToStart -> onDelete()
            else -> return@rememberSwipeToDismissBoxState false
        }
        clicks++
        false
    })
    LaunchedEffect(clicks) {
        dismissState.reset()
    }
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true,
        backgroundContent = {
            val direction = dismissState.dismissDirection

            val bgColor by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surface
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                }
            )
            val iconColor = when (direction) {
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onPrimaryContainer
            }
            val appIcon = when (direction) {
                SwipeToDismissBoxValue.EndToStart -> AppIcon.DELETE
                else -> AppIcon.EDIT
            }
            val alignment = when (direction) {
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                else -> Alignment.CenterStart
            }
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(bgColor),
                contentAlignment = alignment
            ) {
                appIcon.icon(
                    tint = iconColor,
                    modifier = Modifier.padding(24.dp)
                        .fillMaxHeight()
                )
            }
        },
        content = content
    )
}

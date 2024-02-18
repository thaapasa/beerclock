package fi.tuska.beerclock.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
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
    content: @Composable RowScope.() -> Unit
) {
    var clicks by mutableStateOf(0)
    val dismissState = rememberDismissState(confirmValueChange = {
        when (it) {
            DismissValue.DismissedToEnd -> onModify()
            DismissValue.DismissedToStart -> onDelete()
            else -> return@rememberDismissState false
        }
        clicks++
        false
    })
    LaunchedEffect(clicks) {
        dismissState.reset()
    }
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

            val bgColor by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> MaterialTheme.colorScheme.surface
                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                    DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.primaryContainer
                }
            )
            val iconColor = when (direction) {
                DismissDirection.StartToEnd -> MaterialTheme.colorScheme.onPrimaryContainer
                DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
            }
            val appIcon = when (direction) {
                DismissDirection.StartToEnd -> AppIcon.EDIT
                DismissDirection.EndToStart -> AppIcon.DELETE
            }
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
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
        dismissContent = content
    )
}
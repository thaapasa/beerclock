package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Like combinedClickable, but also tracks when the click is released.
 */
@Composable
fun Modifier.combinedClickableReleasable(
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onRelease: () -> Unit,
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val indication: Indication = ripple()
    // detectTapGestures() remembers its contents, so we need wrap callbacks to mutable
    // state so that they are updated properly
    var wrappedClick by remember { mutableStateOf(onClick) }
    LaunchedEffect(onClick) { wrappedClick = onClick }
    var wrappedLongPress by remember { mutableStateOf(onLongPress) }
    LaunchedEffect(onLongPress) { wrappedLongPress = onLongPress }
    var wrappedRelease by remember { mutableStateOf(onRelease) }
    LaunchedEffect(onRelease) { wrappedRelease = onRelease }
    return indication(interactionSource, indication)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset ->
                    val press = PressInteraction.Press(offset)
                    interactionSource.emit(press)
                    tryAwaitRelease()
                    wrappedRelease()
                    val release = PressInteraction.Release(press)
                    interactionSource.emit(release)
                },
                onTap = { wrappedClick() },
                onLongPress = { wrappedLongPress() }
            )
        }
}

/**
 * Like clickable, but reports the offset where the click occurs at as a parameter of the
 * onClick callback.
 */
@Composable
fun Modifier.clickableWithPosition(
    onClick: (offset: Offset) -> Unit,
): Modifier {
    val interactionSrc = remember { MutableInteractionSource() }
    val indication: Indication = ripple()
    return indication(interactionSrc, indication)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset ->
                    val press = PressInteraction.Press(offset)
                    interactionSrc.emit(press)
                    tryAwaitRelease()
                    val release = PressInteraction.Release(press)
                    interactionSrc.emit(release)
                },
                onTap = { onClick(it) },
            )
        }
}

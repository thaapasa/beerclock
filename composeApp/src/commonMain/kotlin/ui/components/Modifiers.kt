package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    val indication: Indication = rememberRipple()
    return indication(interactionSource, indication)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset ->
                    val press = PressInteraction.Press(offset)
                    interactionSource.emit(press)
                    tryAwaitRelease()
                    onRelease()
                    val release = PressInteraction.Release(press)
                    interactionSource.emit(release)
                },
                onTap = { onClick() },
                onLongPress = { onLongPress() },
            )
        }
}

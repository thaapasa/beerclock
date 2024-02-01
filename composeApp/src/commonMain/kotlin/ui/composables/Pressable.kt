package fi.tuska.beerclock.ui.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.pressable(onPress: () -> Unit, onLongPress: () -> Unit): Modifier {
    return pointerInput(Unit) {
        detectTapGestures(
            onLongPress = { onLongPress?.invoke() },
            onPress = { onPress?.invoke() },
        )
    }
}

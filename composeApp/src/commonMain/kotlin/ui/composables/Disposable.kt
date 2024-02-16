package fi.tuska.beerclock.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

interface Disposable {
    fun onDispose()
}

@Composable
inline fun <T : Disposable> rememberWithDispose(
    crossinline instanceCreator: @DisallowComposableCalls () -> T,
): T {
    val instance = remember { instanceCreator() }

    DisposableEffect(instance) {
        onDispose { instance.onDispose() }
    }

    return instance
}

@Composable
inline fun <T : Disposable> rememberWithDispose(
    key: Any?,
    crossinline instanceCreator: @DisallowComposableCalls () -> T,
): T {
    val instance = remember(key) { instanceCreator() }

    DisposableEffect(instance) {
        onDispose { instance.onDispose() }
    }

    return instance
}
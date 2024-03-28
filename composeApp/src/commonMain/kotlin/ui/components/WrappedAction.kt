package fi.tuska.beerclock.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

/**
 * Returns a stable reference to a function that will always call the latest version of the
 * given action parameter. You can use this in cases where a function reference is remembered
 * somewhere and you need to be able to call the latest version of some function instead of
 * the one passed when the value was initialized.
 */
@Composable
fun WrappedAction(action: () -> Unit): () -> Unit {
    var ref by remember { mutableStateOf(action) }
    ref = action
    LaunchedEffect(action) {
        ref = action
    }
    val scope = rememberCoroutineScope()
    return { scope.launch { ref.invoke() } }
}

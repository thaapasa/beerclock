package fi.tuska.beerclock.ui.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun CoroutineScope.showNotificationWithCancel(
    snackbar: SnackbarHostState,
    message: String,
    cancelLabel: String,
    cancelAction: suspend () -> Unit,
) {
    launch(Dispatchers.Main) {
        val result =
            snackbar.showSnackbar(
                message,
                actionLabel = cancelLabel,
                duration = SnackbarDuration.Short
            )
        if (result == SnackbarResult.ActionPerformed) {
            withContext(Dispatchers.IO) {
                cancelAction()
            }
        }
    }
}

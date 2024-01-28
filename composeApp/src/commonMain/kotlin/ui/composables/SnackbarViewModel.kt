package fi.tuska.beerclock.ui.composables

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class SnackbarViewModel(val snackbar: SnackbarHostState) : ViewModel() {

    fun showMessage(message: String) {
        launch(Dispatchers.Main) {
            snackbar.showSnackbar(message)
        }
    }

}

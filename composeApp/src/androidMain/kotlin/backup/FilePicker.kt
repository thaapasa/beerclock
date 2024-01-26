package fi.tuska.beerclock.backup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

enum class FileMode {
    CREATE, OPEN
}

@Composable
fun FilePicker(
    onFilePicked: (Uri?) -> Unit,
    mode: FileMode = FileMode.OPEN,
    initialName: String? = null,
    content: @Composable (pickFile: () -> Unit) -> Unit,
) {
    val openDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onFilePicked(result.data?.data)
        } else {
            onFilePicked(null)
        }
    }

    val intent = remember { createIntent(mode, initialName) }

    content {
        openDocumentLauncher.launch(intent)
    }
}

internal fun createIntent(mode: FileMode, initialName: String?): Intent = when (mode) {
    FileMode.OPEN ->
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/*"
        }

    FileMode.CREATE ->
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = "application/*"
            initialName?.let { putExtra(Intent.EXTRA_TITLE, it) }
        }
}


package fi.tuska.beerclock.backup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun FilePicker(
    onFilePicked: (Uri?) -> Unit,
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

    val intent = remember {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/*"
        }
    }

    content {
        openDocumentLauncher.launch(intent)
    }
}

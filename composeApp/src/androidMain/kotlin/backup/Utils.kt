package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import fi.tuska.beerclock.logging.getLogger
import java.io.File

private val logger = getLogger("BackupUtils")

fun Uri.copyTo(context: Context, targetFile: File) = try {
    context.contentResolver.openInputStream(this)?.use { input ->
        targetFile.outputStream().use { output ->
            input.copyTo(output)
            true
        }
    } ?: false
} catch (e: Exception) {
    logger.error("Error copying file $this to $targetFile: ${e.message}")
    false
}

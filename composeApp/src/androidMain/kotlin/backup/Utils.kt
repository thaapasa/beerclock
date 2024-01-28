package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import fi.tuska.beerclock.logging.getLogger
import java.io.File
import java.io.FileInputStream

private val logger = getLogger("Utils")

fun Uri.copyTo(context: Context, targetFile: File) =
    context.contentResolver.openInputStream(this)?.use { input ->
        targetFile.outputStream().use { output ->
            input.copyTo(output)
        }
    } ?: 0

fun File.copyToUri(context: Context, targetFile: Uri) =
    FileInputStream(this).use { input ->
        context.contentResolver.openOutputStream(targetFile)
            ?.use { output -> input.copyTo(output) }
    } ?: 0


fun <T> File.processLocally(
    context: Context,
    suffix: String = ".tmp",
    process: (file: File) -> T,
): T =
    processLocally(context, this, suffix, process)

fun <T> Uri.processLocally(
    context: Context,
    suffix: String = ".tmp",
    process: (file: File) -> T,
): T =
    processLocally(context, this, suffix, process)

private fun <T> processLocally(
    context: Context,
    source: Any,
    suffix: String = ".tmp",
    process: (file: File) -> T,
): T {
    val target = File.createTempFile("beerclock", suffix)
    try {
        logger.debug("Importing $source file to $target for internal processing")
        if (source is File) {
            source.copyTo(target)
        } else if (source is Uri) {
            source.copyTo(context, target)
        } else {
            throw IllegalArgumentException("Don't know how to import ${source.javaClass.name}")
        }
        return process(target)
    } finally {
        when (target.delete()) {
            true -> logger.debug("Deleted temporary import file $target")
            false -> logger.warn("Could not delete temporary import file $target")
        }
    }
}


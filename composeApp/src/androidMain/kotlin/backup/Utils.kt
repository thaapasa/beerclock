package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream


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

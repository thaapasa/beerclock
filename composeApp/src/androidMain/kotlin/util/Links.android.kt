package fi.tuska.beerclock.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import fi.tuska.beerclock.logging.getLogger
import org.koin.java.KoinJavaComponent

private val logger = getLogger("Links")

actual fun openLink(url: String) {
    logger.info("Opening $url on device...")
    val context: Context by KoinJavaComponent.inject(Context::class.java)

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

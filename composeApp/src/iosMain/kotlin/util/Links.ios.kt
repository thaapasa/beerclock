package fi.tuska.beerclock.util

import fi.tuska.beerclock.logging.getLogger
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

private val logger = getLogger("Links")

actual fun openLink(url: String) {
    logger.info("Opening link $url on device...")
    val nsUrl = NSURL(string = url)
    UIApplication.sharedApplication.openURL(nsUrl)
}

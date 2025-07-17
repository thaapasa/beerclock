package fi.tuska.beerclock.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import fi.tuska.beerclock.logging.getLogger
import org.koin.java.KoinJavaComponent.inject

private val logger = getLogger("AppVersion")

actual fun getAppVersion(): String {
    val context: Context by inject(Context::class.java)
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "?"
    } catch (e: PackageManager.NameNotFoundException) {
        logger.warn("Could not get app version: ${e.message}")
        "?"
    }
}

actual fun getOSVersion(): String {
    return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
}

actual fun getDeviceModel(): String {
    return "${Build.MANUFACTURER} ${Build.MODEL}"
}

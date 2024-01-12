package fi.tuska.beerclock.util

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

actual fun getAppVersion(): String {
    val infoDictionary = NSBundle.mainBundle.infoDictionary
    return infoDictionary?.get("CFBundleShortVersionString") as? String ?: "?"
}

actual fun getOSVersion(): String {
    return "${UIDevice.currentDevice.systemName} ${UIDevice.currentDevice.systemVersion}"
}

actual fun getDeviceModel(): String {
    return UIDevice.currentDevice.name
}

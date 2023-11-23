package fi.tuska.beerclock.logging

import android.util.Log

class AndroidLogger(private val tag: String) : Logger {

    override fun debug(message: String) {
        Log.d(tag, message)
    }

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun warn(message: String) {
        Log.w(tag, message)
    }

    override fun error(message: String) {
        Log.e(tag, message)
    }

}

actual fun getLogger(tag: String): Logger {
    return AndroidLogger(tag)
}

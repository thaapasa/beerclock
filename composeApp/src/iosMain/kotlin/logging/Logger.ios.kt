package fi.tuska.beerclock.logging

class IosLogger(private val tag: String) : Logger {
    override fun debug(message: String) {
        this.doLog("DEBUG", message)
    }

    override fun info(message: String) {
        this.doLog("INFO", message)
    }

    override fun warn(message: String) {
        this.doLog("WARN", message)
    }

    override fun error(message: String) {
        this.doLog("ERROR", message)
    }

    private fun doLog(level: String, message: String) {
        println("$level [$tag] $message")
    }
}

actual fun getLogger(tag: String): Logger {
    return IosLogger(tag)
}

package fi.tuska.beerclock.logging

interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
}

expect fun getLogger(tag: String): Logger

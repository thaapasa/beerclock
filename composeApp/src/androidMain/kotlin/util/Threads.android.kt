package fi.tuska.beerclock.util

actual fun getCurrentThreadName(): String {
    return Thread.currentThread().name
}

package fi.tuska.beerclock.wear

actual fun isWatchSupported(): Boolean = false

actual suspend fun sendCurrentBacStatusToWatch(status: CurrentBacStatus) {
    // Noop on iOS
}

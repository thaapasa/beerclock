package fi.tuska.beerclock.wear

actual suspend fun sendCurrentBacStatusToWatch(state: CurrentBacStatus) {
    // Noop on iOS
}

package fi.tuska.beerclock.backup

actual fun isJAlcoMeterImportSupported(): Boolean {
    return false
}

actual fun importDataFromJAlcoMeter() {
    // Not possible, no-op
}

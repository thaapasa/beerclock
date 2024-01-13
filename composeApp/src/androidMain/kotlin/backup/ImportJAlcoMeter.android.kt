package fi.tuska.beerclock.backup

import fi.tuska.beerclock.logging.getLogger

private val logger = getLogger("JAlcoMeterImporter")

actual fun isJAlcoMeterImportSupported(): Boolean {
    return true
}

actual fun importDataFromJAlcoMeter() {
    logger.info("Starting data import from jAlcoMeter...")
}

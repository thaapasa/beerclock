package fi.tuska.beerclock.backup.jalcometer

import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase

private val logger = getLogger("JAlcoMeterDataImporter")

fun importJAlcometerData(db: SQLiteDatabase) {
    logger.info("Starting jAlcoMeter data import")
    importHistory(db)
    logger.info("jAlcoMeter data import complete!")
}

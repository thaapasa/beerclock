package fi.tuska.beerclock.backup.jalcometer

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase
import org.koin.java.KoinJavaComponent

private val logger = getLogger("JAlcoMeterDataImporter")

fun importJAlcometerData(db: SQLiteDatabase, showStatus: (status: ImportStatus) -> Unit) {
    logger.info("Starting jAlcoMeter data import")
    val targetDb: BeerDatabase by KoinJavaComponent.inject(BeerDatabase::class.java)

    val categories = readCategories(db)
    logger.info("Existing categories: $categories")
    val drinks = importDrinkLibrary(db, targetDb, categories, showStatus)
    importHistory(db, targetDb, showStatus)
    logger.info("jAlcoMeter data import complete!")
}

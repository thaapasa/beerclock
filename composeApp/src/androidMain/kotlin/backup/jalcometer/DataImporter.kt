package fi.tuska.beerclock.backup.jalcometer

import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase

private val logger = getLogger("JAlcoMeterDataImporter")

data class ImportContext(
    val db: SQLiteDatabase,
    val drinkService: DrinkService,
    val showStatus: (status: ImportStatus) -> Unit,
)

fun importJAlcometerData(ctx: ImportContext) {
    logger.info("Starting jAlcoMeter data import")

    val categories = readCategories(ctx)
    logger.info("Existing categories: $categories")
    importDrinkLibrary(ctx, categories)
    importHistory(ctx)
    logger.info("jAlcoMeter data import complete!")
}

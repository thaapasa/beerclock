package fi.tuska.beerclock.database

import android.database.Cursor
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase
import java.io.File

private val logger = getLogger("AndroidDbUtils")

fun Cursor.toSequence(): Sequence<Cursor> {
    val c = this
    return sequence {
        while (c.moveToNext()) {
            yield(c)
        }
    }
}

fun <T> BeerDatabase.batchOperate(
    source: Sequence<T>,
    batchSize: Int,
    afterEach: ((completed: Long) -> Unit)? = null,
    operation: (row: T) -> Unit,
) {
    var batch = 0
    var completed = 0L
    val iterator = source.iterator()
    while (iterator.hasNext()) {
        batch++
        var remainingForThisBatch = batchSize
        logger.debug("Starting transaction for batch $batch")
        transaction {
            while (remainingForThisBatch-- > 0 && iterator.hasNext()) {
                val row = iterator.next()
                operation(row)
                completed++
            }
        }
        afterEach?.invoke(completed)
        logger.debug("Ending transaction for batch $batch")
    }
}

fun <T> processSQLiteDatabase(file: File, process: (db: SQLiteDatabase) -> T): T =
    SQLiteDatabase.openDatabase(
        file.path, null, SQLiteDatabase.OPEN_READONLY
    ).use { process(it) }


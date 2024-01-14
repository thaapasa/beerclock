package fi.tuska.beerclock.database

import android.database.Cursor
import fi.tuska.beerclock.logging.getLogger

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
    operation: (row: T) -> Unit,
) {
    var batch = 0
    val iterator = source.iterator()
    while (iterator.hasNext()) {
        batch++
        var remainingForThisBatch = batchSize
        logger.debug("Starting transaction for batch $batch")
        transaction {
            while (remainingForThisBatch-- > 0 && iterator.hasNext()) {
                val row = iterator.next()
                operation(row)
            }
        }
        logger.debug("Ending transaction for batch $batch")
    }
}

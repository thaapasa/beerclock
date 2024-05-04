package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.drinks.asFlow
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("MixedDrinks")

class MixedDrinksService : KoinComponent {
    val db: BeerDatabase = get()

    suspend fun insertDrinkMix(mix: MixedDrink) {
        withContext(Dispatchers.IO) {
            val info = mix.info
            db.transaction {
                db.mixedDrinkQueries.insertMixedDrink(
                    name = info.name,
                    instructions = info.instructions,
                    image = info.image.name,
                    category = info.category?.name,
                )
                val rowId = db.mixedDrinkQueries.lastInsertedId().executeAsOne()
                val items = mix.items
                items.forEachIndexed { index, item ->
                    db.mixedDrinkQueries.insertDrinkComponent(
                        mixId = rowId,
                        ord = index.toLong(),
                        name = item.name,
                        abvPercentage = item.abvPercentage,
                        quantityLiters = item.quantityCl / 100.0,
                        amount = item.amount,
                    )
                }
                logger.info("Inserted new mixed drink with id $rowId")
            }
        }
    }

    suspend fun updateDrinkMix(id: Long, mix: MixedDrink) {
        withContext(Dispatchers.IO) {
            db.transaction {
                val info = mix.info
                db.mixedDrinkQueries.updateMixedDrink(
                    id = id,
                    name = info.name,
                    instructions = info.instructions,
                    image = info.image.name,
                    category = info.category?.name,
                )
                val itemIds = mutableListOf<Long>()
                mix.items.forEachIndexed { index, item ->
                    if (item.id != null) {
                        db.mixedDrinkQueries.updateDrinkComponent(
                            id = item.id,
                            ord = index.toLong(),
                            amount = item.amount,
                            name = item.name,
                            abvPercentage = item.abvPercentage,
                            quantityLiters = item.quantityCl / 100.0,
                        )
                        itemIds.add(item.id)
                    } else {
                        db.mixedDrinkQueries.insertDrinkComponent(
                            mixId = id,
                            ord = index.toLong(),
                            amount = item.amount,
                            name = item.name,
                            abvPercentage = item.abvPercentage,
                            quantityLiters = item.quantityCl / 100.0,
                        )
                        val itemId = db.mixedDrinkQueries.lastInsertedId().executeAsOne()
                        itemIds.add(itemId)
                    }
                }
                db.mixedDrinkQueries.clearDrinkComponents(mixId = id, retainItemIds = itemIds)
            }
        }
    }

    suspend fun deleteDrinkMix(id: Long) {
        withContext(Dispatchers.IO) {
            db.mixedDrinkQueries.deleteMixedDrink(id = id)
        }
    }

    fun flowMixedDrinks(): Flow<List<MixedDrinkInfo>> {
        val drinks = db.mixedDrinkQueries.getMixedDrinks().asFlow()
        return drinks.map { it.map(MixedDrinkInfo::fromRecord) }.flowOn(Dispatchers.IO)
    }

    suspend fun getDrinkMix(id: Long): MixedDrink {
        return withContext(Dispatchers.IO) {
            db.transactionWithResult {
                val info = db.mixedDrinkQueries.getMixedDrink(id).executeAsOne()
                val items = db.mixedDrinkQueries.getMixComponents(id).executeAsList()
                MixedDrink(MixedDrinkInfo.fromRecord(info), items.map(MixedDrinkItem::fromRecord))
            }
        }
    }
}

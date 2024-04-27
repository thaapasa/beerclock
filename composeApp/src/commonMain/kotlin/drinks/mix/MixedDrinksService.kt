package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.drinks.asFlow
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("MixedDrinks")

class MixedDrinksService : KoinComponent {
    val db: BeerDatabase = get()

    suspend fun insertDrinkMix(mix: MixedDrinkInfo) {
        withContext(Dispatchers.IO) {
            db.mixedDrinkQueries.insertMixedDrink(
                name = mix.name,
                image = mix.image.name,
                category = mix.category?.name,
                created = Clock.System.now().toDbTime()
            )
            val rowId = db.mixedDrinkQueries.lastInsertedId().executeAsOne()
            logger.info("Inserted new mixed drink with id $rowId")
        }
    }

    fun flowMixedDrinks(): Flow<List<MixedDrinkInfo>> {
        val drinks = db.mixedDrinkQueries.getMixedDrinks().asFlow()
        return drinks.map { it.map(MixedDrinkInfo::fromRecord) }.flowOn(Dispatchers.IO)
    }
}

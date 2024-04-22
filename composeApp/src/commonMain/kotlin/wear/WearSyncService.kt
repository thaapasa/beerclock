package fi.tuska.beerclock.wear

import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("WearSync")

class WearSyncService(private val drinkService: DrinkService) : KoinComponent {
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()

    suspend fun getCurrentBacStatus(preloadedDrinks: List<DrinkRecordInfo>?): CurrentBacStatus {
        val today = times.currentDrinkDay()
        val dayStart = times.dayStartTime(today)
        val drinks = preloadedDrinks ?: drinkService.getDrinksForHomeScreen(today)
        val bacStatus = BacStatus(drinks, today)
        val dailyUnits = drinks.filter { it.time >= dayStart }.sumOf { it.units() }
        return CurrentBacStatus(
            locale = prefs.prefs.locale?.locale,
            time = Clock.System.now(),
            dailyUnits = dailyUnits,
            dayEndTime = times.dayEndTime(today),
            maxDailyUnits = prefs.prefs.maxDailyUnits,
            alcoholGrams = bacStatus.atTime(Clock.System.now()).alcoholGrams,
            volumeOfDistribution = prefs.prefs.volumeOfDistribution,
            maxBac = prefs.prefs.maxBac
        )
    }

    suspend fun sendStatusToWatch(preloadedDrinks: List<DrinkRecordInfo>? = null) {
        if (!isWatchSupported()) {
            logger.debug("Watch not supported, skipping status sync")
            return
        }
        val status = getCurrentBacStatus(preloadedDrinks)
        sendCurrentBacStatusToWatch(status)
    }
}

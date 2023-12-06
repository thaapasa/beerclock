package fi.tuska.beerclock.bac

import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkTimeService
import org.koin.core.component.KoinComponent
import kotlin.time.Duration.Companion.minutes

class BacStatus(sortedInputDrinks: List<DrinkRecordInfo>) : KoinComponent {

    private val times = DrinkTimeService()
    private val dayStart = times.dayStartTime()

    /**
     * List of the different BAC levels at different points during the day, measured in
     * grams of unburned alcohol in your body.
     * Includes events at the start and end of the day, recording the states there.
     */
    private val instantEvents: List<AlcoholAtTime>
    val graphData: BacGraphData
    val graphData2: BacGraphData

    init {
        val (before, today) = sortedInputDrinks.partition { it.time < dayStart }
        val alcoholAtDayStart = AlcoholRemaining.forDrinks(before, dayStart)
        instantEvents = InstantBacCalculator.calculate(alcoholAtDayStart, today)
        val estimatedEvents = AbsorbEstimatingBacCalculator.calculate(alcoholAtDayStart, today)
        graphData = BacGraphData.smoothed(estimatedEvents, 3.minutes)
        graphData2 = BacGraphData(estimatedEvents)
    }

}

package fi.tuska.beerclock.screens

import androidx.test.ext.junit.runners.AndroidJUnit4
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.CategoryStatistics
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.StatisticsByCategory
import fi.tuska.beerclock.drinks.drinkDef
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.screens.about.AboutScreen
import fi.tuska.beerclock.screens.disclosure.DisclosureScreen
import fi.tuska.beerclock.screens.drinks.create.AddDrinkScreen
import fi.tuska.beerclock.screens.drinks.modify.EditDrinkScreen
import fi.tuska.beerclock.screens.history.HistoryScreen
import fi.tuska.beerclock.screens.library.DrinkLibraryScreen
import fi.tuska.beerclock.screens.library.create.CreateDrinkInfoScreen
import fi.tuska.beerclock.screens.library.modify.EditDrinkInfoScreen
import fi.tuska.beerclock.screens.newdrink.NewDrinkSearchScreen
import fi.tuska.beerclock.screens.settings.SettingsScreen
import fi.tuska.beerclock.screens.statistics.MonthlyStatisticsData
import fi.tuska.beerclock.screens.statistics.StatisticsMonth
import fi.tuska.beerclock.screens.statistics.StatisticsScreen
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.settings.UserPreferences
import fi.tuska.beerclock.util.parcelizeAndRead
import junit.framework.TestCase
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

@RunWith(AndroidJUnit4::class)
class ScreenParcelizationTest {

    @Test
    fun shouldParcelizeHistoryScreen() {
        val date = LocalDate(2021, 5, 14)
        val screen = HistoryScreen(startDate = date, initialDailyGaugeValue = 3.5)
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(date, newScreen.startDate)
        assertEquals(3.5, newScreen.initialDailyGaugeValue)
        assertEquals(0.0, newScreen.initialWeeklyGaugeValue)
    }

    @Test
    fun shouldParcelizeAddDrinkScreen() {
        val date = LocalDate(2021, 5, 14)
        val screen = AddDrinkScreen(date = date, proto = testDrink)
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(date, newScreen.date)
        verifyReadDrinkData(newScreen.proto)
    }

    @Test
    fun shouldParcelizeEditDrinkInfoScreen() {
        val screen = EditDrinkInfoScreen(drink = DrinkInfo(id = 14, info = testDrink))
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(14, newScreen.drink.id)
        verifyReadDrinkData(newScreen.drink)
    }

    @Test
    fun shouldParcelizeNewDrinkSearchScreen() {
        val date = LocalDate(2021, 5, 14)
        val screen = NewDrinkSearchScreen(date = date, searchString = "Kalia")
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(date, newScreen.date)
        assertEquals("Kalia", newScreen.searchString)
    }

    @Test
    fun shouldParcelizeStatisticsScreen() {
        val date = LocalDate(2021, 5, 14)
        val period = StatisticsMonth(date)
        val prevData = MonthlyStatisticsData(
            period = period,
            categoryStatistics = StatisticsByCategory(
                byCategories = listOf(
                    CategoryStatistics(
                        title = "Kaliat",
                        categoryImage = DrinkImage.CAT_BEERS,
                        totalUnits = 2.0,
                        totalQuantityLiters = 1.1,
                        drinkCount = 2,
                        order = 1,
                    )
                ),
                period = period,
                prefs = UserPreferences()
            ),
            units = listOf(date to 2.1),
            maxDailyUnits = 7.0
        )
        val screen = StatisticsScreen(period = period, previousData = prevData)
        val newScreen = parcelizeAndRead(screen)
        assertEquals(period, newScreen.period)
        // Previous data should serialize to null
        assertNull(newScreen.previousData)
    }

    @Test
    fun shouldParcelizeEditDrinkScreen() {
        val time = Clock.System.now()
        val screen =
            EditDrinkScreen(drink = DrinkRecordInfo(id = 28, time = time, info = testDrink))
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(28, newScreen.drink.id)
        assertEquals(time, newScreen.drink.time)
        verifyReadDrinkData(newScreen.drink)
    }

    @Test
    fun shouldParcelizeDrinkLibraryScreen() {
        val screen = DrinkLibraryScreen(initialCategory = Category.SPIRITS)
        val newScreen = parcelizeAndRead(screen)
        assertEquals(screen, newScreen)
        assertEquals(Category.SPIRITS, newScreen.initialCategory)
    }

    @Test
    fun shouldParcelizeSingletonScreens() {
        testSingletonScreenParcelization(AboutScreen)
        testSingletonScreenParcelization(SettingsScreen)
        testSingletonScreenParcelization(DisclosureScreen)
        testSingletonScreenParcelization(HomeScreen)
        testSingletonScreenParcelization(CreateDrinkInfoScreen)
    }

    private fun testSingletonScreenParcelization(singletonScreen: Screen) {
        val newScreen = parcelizeAndRead(singletonScreen)
        assertEquals(singletonScreen, newScreen)
        TestCase.assertSame(singletonScreen, newScreen)
    }

    private fun verifyReadDrinkData(data: BasicDrinkInfo?) {
        assertEquals("Pangalaktinen kurlauspommi", data?.name)
        assertSame(DrinkImage.ABSINTHE_1, data?.image)
        assertEquals(68.3, data?.abvPercentage)
        assertSame(Category.COCKTAILS, data?.category)
    }

    private val testDrink = drinkDef(
        category = Category.COCKTAILS,
        name = "Pangalaktinen kurlauspommi",
        image = DrinkImage.ABSINTHE_1,
        abvPercentage = 68.3
    )
}


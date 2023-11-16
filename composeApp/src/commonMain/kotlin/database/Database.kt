package fi.tuska.beerclock.common.database

import androidx.compose.runtime.staticCompositionLocalOf
import app.cash.sqldelight.db.SqlDriver

expect object DriverFactory {
    fun createDriver(): SqlDriver
}

private fun createDatabase(): BeerDatabase {
    val driver = DriverFactory.createDriver()
    return BeerDatabase(driver)
}

object DatabaseProvider {
    val database: BeerDatabase by lazy { createDatabase() }
}

// Compose ambient to provide database access
val LocalDatabase = staticCompositionLocalOf { DatabaseProvider.database }

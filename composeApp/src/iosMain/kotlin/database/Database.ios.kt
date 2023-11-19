package fi.tuska.beerclock.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import fi.tuska.beerclock.database.BeerDatabase

actual object DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(BeerDatabase.Schema, "beerdatabase.db")
    }
}

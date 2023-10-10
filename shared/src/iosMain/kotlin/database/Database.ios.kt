package fi.tuska.beerclock.common.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(BeerDatabase.Schema, "beerdatabase.db")
    }
}
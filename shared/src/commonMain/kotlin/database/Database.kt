package fi.tuska.beerclock.common.database

import app.cash.sqldelight.db.SqlDriver

expect object DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(): BeerDatabase {
    val driver = DriverFactory.createDriver()
    val database = BeerDatabase(driver)

    // Do more work with the database (see below).

    return database
}

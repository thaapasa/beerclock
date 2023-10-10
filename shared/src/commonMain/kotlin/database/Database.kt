package fi.tuska.beerclock.common.database

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): BeerDatabase {
    val driver = driverFactory.createDriver()
    val database = BeerDatabase(driver)

    // Do more work with the database (see below).

    return database
}

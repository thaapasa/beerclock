package fi.tuska.beerclock.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import fi.tuska.beerclock.database.BeerDatabase
import org.koin.dsl.module

/**
 * Define Android-specific dependencies
 */
actual fun platformModule() = module {
    single {
        BeerDatabase(NativeSqliteDriver(BeerDatabase.Schema, "beerdatabase.db"))
    }
}


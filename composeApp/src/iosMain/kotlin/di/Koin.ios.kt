package fi.tuska.beerclock.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.settings.IosPreferenceStore
import fi.tuska.beerclock.settings.PreferenceStore
import org.koin.dsl.module

/**
 * Define Android-specific dependencies
 */
actual fun platformModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = BeerDatabase.Schema,
            name = "beerdatabase.db",
        )
    }
    single<PreferenceStore> { IosPreferenceStore() }
}


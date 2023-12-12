package fi.tuska.beerclock.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.settings.AndroidPreferenceStore
import fi.tuska.beerclock.settings.PreferenceStore
import org.koin.dsl.module

/**
 * Define Android-specific dependencies
 */
actual fun platformModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = BeerDatabase.Schema,
            context = get(),
            name = "beerdatabase.db",
        )
    }
    single<PreferenceStore> { AndroidPreferenceStore(get()) }
}

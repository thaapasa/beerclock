package fi.tuska.beerclock.di

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.DatabaseInfo
import fi.tuska.beerclock.database.DbInfoQueries
import fi.tuska.beerclock.events.EventBus
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.settings.UserStore
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(platformModule(), commonModule())
    }

// called by iOS from iOSApp.swift: KoinKt.doInitKoin()
@Suppress("UNUSED")
fun initKoin() = initKoin {}

fun commonModule() = module {
    single { BeerDatabase(get()) }
    single(createdAtStart = true) { GlobalUserPreferences(UserStore.load(get())) }
    single(createdAtStart = true) {
        // Setup SQLite to follow foreign key constraint
        // This needs to be activated manually: https://sqlite.org/pragma.html#pragma_foreign_keys
        DbInfoQueries(get()).setupForeignKeyConstraints()
    }
    single(createdAtStart = true) {
        DatabaseInfo(
            sqliteVersion = DbInfoQueries(get()).dbVersion().executeAsOne(),
            databaseVersion = BeerDatabase.Schema.version
        )
    }
    single(createdAtStart = true) { EventBus() }
}

expect fun platformModule(): Module

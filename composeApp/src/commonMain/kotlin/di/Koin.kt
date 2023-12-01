package fi.tuska.beerclock.di

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
    single(createdAtStart = true) { GlobalUserPreferences(UserStore.load(get())) }
}

expect fun platformModule(): Module

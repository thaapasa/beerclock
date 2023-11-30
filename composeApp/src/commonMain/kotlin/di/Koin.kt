package fi.tuska.beerclock.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(platformModule(), commonModule())
    }

// called by iOS
fun initKoin() = initKoin() {}

fun commonModule() = module {
    single { }
}

expect fun platformModule(): Module

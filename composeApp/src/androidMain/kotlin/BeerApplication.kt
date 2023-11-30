package fi.tuska.beerclock

import android.app.Application
import fi.tuska.beerclock.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class BeerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(level = Level.INFO)
            androidContext(applicationContext)
        }
    }
}

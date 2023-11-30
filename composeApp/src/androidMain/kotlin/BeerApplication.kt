package fi.tuska.beerclock

import android.app.Application
import fi.tuska.beerclock.di.initKoin
import org.koin.android.ext.koin.androidContext

class BeerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(applicationContext)
        }
    }
}

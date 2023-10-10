package fi.tuska.beerclock.common.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual object DriverFactory {
  /** Initialized by PreferenceInitializer */
  lateinit var applicationContext: Context
    internal set

  actual fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(BeerDatabase.Schema, applicationContext, "beerdatabase.db")
  }
}

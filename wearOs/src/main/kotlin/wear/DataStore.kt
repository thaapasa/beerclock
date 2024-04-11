package fi.tuska.beerclock.wear

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fi.tuska.beerclock.defaultTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "CurrentBacStatus")

private val UPDATE_TIME = longPreferencesKey("update_time")
private val DAILY_UNITS = doublePreferencesKey("daily_units")
private val ALCOHOL_GRAMS = doublePreferencesKey("alcohol_grams")
private val VOLUME_OF_DISTRIBUTION = doublePreferencesKey("volume_of_distribution")


fun CurrentBacStatus.Companion.flowCurrentState(context: Context): Flow<CurrentBacStatus> =
    context.dataStore.data
        .map { preferences ->
            CurrentBacStatus(
                time = Instant.ofEpochMilli(preferences[UPDATE_TIME] ?: defaultTime),
                dailyUnits = preferences[DAILY_UNITS] ?: 0.0,
                alcoholGrams = preferences[ALCOHOL_GRAMS] ?: 0.0,
                volumeOfDistribution = preferences[VOLUME_OF_DISTRIBUTION] ?: 50.0
            )
        }

suspend fun CurrentBacStatus.Companion.getState(context: Context): CurrentBacStatus =
    flowCurrentState(context).first()


suspend fun CurrentBacStatus.saveState(
    context: Context,
) {
    context.dataStore.edit { settings ->
        settings[UPDATE_TIME] = time.toEpochMilli()
        settings[DAILY_UNITS] = dailyUnits
        settings[ALCOHOL_GRAMS] = alcoholGrams
        settings[VOLUME_OF_DISTRIBUTION] = volumeOfDistribution
    }
}

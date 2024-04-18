package fi.tuska.beerclock.wear

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fi.tuska.beerclock.defaultTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "CurrentBacStatus")

private val LOCALE = stringPreferencesKey("locale")
private val UPDATE_TIME = longPreferencesKey("updateTime")
private val MAX_DAILY_UNITS = doublePreferencesKey("maxDailyUnits")
private val DAILY_UNITS = doublePreferencesKey("dailyUnits")
private val ALCOHOL_GRAMS = doublePreferencesKey("alcoholGrams")
private val VOLUME_OF_DISTRIBUTION = doublePreferencesKey("volumeOfDistribution")
private val MAX_BAC = doublePreferencesKey("maxBac")


fun CurrentBacStatus.Companion.flowCurrentState(context: Context): Flow<CurrentBacStatus> =
    context.dataStore.data
        .map { preferences ->
            CurrentBacStatus(
                languageTag = preferences[LOCALE]?.ifBlank { null },
                time = Instant.ofEpochMilli(preferences[UPDATE_TIME] ?: defaultTime),
                dailyUnits = preferences[DAILY_UNITS] ?: 0.0,
                maxDailyUnits = preferences[MAX_DAILY_UNITS] ?: 7.0,
                alcoholGrams = preferences[ALCOHOL_GRAMS] ?: 0.0,
                volumeOfDistribution = preferences[VOLUME_OF_DISTRIBUTION] ?: 50.0,
                maxBac = preferences[MAX_BAC] ?: 1.5,
            )
        }

suspend fun CurrentBacStatus.Companion.getState(context: Context): CurrentBacStatus =
    flowCurrentState(context).first()


suspend fun CurrentBacStatus.saveState(
    context: Context,
) {
    context.dataStore.edit { settings ->
        settings[LOCALE] = languageTag ?: ""
        settings[UPDATE_TIME] = time.toEpochMilli()
        settings[DAILY_UNITS] = dailyUnits
        settings[MAX_DAILY_UNITS] = maxDailyUnits
        settings[ALCOHOL_GRAMS] = alcoholGrams
        settings[VOLUME_OF_DISTRIBUTION] = volumeOfDistribution
        settings[MAX_BAC] = maxBac
    }
}

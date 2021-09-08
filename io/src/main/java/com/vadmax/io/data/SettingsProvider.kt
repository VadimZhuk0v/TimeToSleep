package com.vadmax.io.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val SHARED_NAME = "shared"

private val VL_IS_TIMER_ENABLED = booleanPreferencesKey("VL_SKIP_TUTORIAL")
private val VL_SELECTED_TIME = longPreferencesKey("VL_SELECTED_TIME")

interface SettingsProvider {

    val isTimerEnabled: Flow<Boolean>

    suspend fun setTimerEnable(value: Boolean)

    suspend fun setSelectedTime(time: Long?)

    suspend fun getSelectedTime(): Long?
}

private val Context.dataStore by preferencesDataStore(name = SHARED_NAME)

class SettingsProviderImpl(private val context: Context) : SettingsProvider {

    override val isTimerEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[VL_IS_TIMER_ENABLED] ?: false
    }

    override suspend fun setTimerEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_TIMER_ENABLED] = value
        }
    }

    override suspend fun getSelectedTime() =
        context.dataStore.data.map { it[VL_SELECTED_TIME] }.first()

    override suspend fun setSelectedTime(time: Long?) {
        context.dataStore.edit {
            time ?: run {
                it.remove(VL_SELECTED_TIME)
                return@edit
            }
            it[VL_SELECTED_TIME] = time
        }
    }
}

package com.vadmax.io.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SHARED_NAME = "shared"

private val VL_IS_TIMER_ENABLED = booleanPreferencesKey("VL_SKIP_TUTORIAL")

interface SettingsProvider {

    val isTimerEnabled: Flow<Boolean>

    suspend fun setTimerEnable(value: Boolean)
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
}

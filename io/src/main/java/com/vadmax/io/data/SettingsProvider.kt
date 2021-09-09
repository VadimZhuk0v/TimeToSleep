package com.vadmax.io.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.vadmax.io.utils.extentions.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val SHARED_NAME = "shared"

private val VL_IS_TIMER_ENABLED = booleanPreferencesKey("VL_SKIP_TUTORIAL")
private val VL_SELECTED_TIME = longPreferencesKey("VL_SELECTED_TIME")
private val VL_SELECTED_APPS = stringPreferencesKey("VL_SELECTED_APPS")

interface SettingsProvider {

    val isTimerEnabled: Flow<Boolean>
    val selectedAppsFlow: Flow<List<AppInfo>>

    suspend fun setTimerEnable(value: Boolean)

    suspend fun setSelectedTime(time: Long?)

    suspend fun getSelectedTime(): Long?

    suspend fun selectApp(appInfo: AppInfo)

    suspend fun removeApp(appInfo: AppInfo)
}

private val Context.dataStore by preferencesDataStore(name = SHARED_NAME)

class SettingsProviderImpl(private val context: Context) : SettingsProvider {

    override val isTimerEnabled: Flow<Boolean> = context.dataStore.data.map {
        it[VL_IS_TIMER_ENABLED] ?: false
    }
    override val selectedAppsFlow = context.dataStore.data.map {
        val json = it[VL_SELECTED_APPS] ?: return@map emptyList<AppInfo>()
        Gson().toObject(json)
    }

    override suspend fun setTimerEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_TIMER_ENABLED] = value
        }
    }

    override suspend fun getSelectedTime() =
        context.dataStore.data.map { it[VL_SELECTED_TIME] }.first()

    override suspend fun selectApp(appInfo: AppInfo) {
        context.dataStore.edit {
            val json = it[VL_SELECTED_APPS]
            val list = if (json == null) {
                mutableListOf<AppInfo>()
            } else {
                Gson().toObject(json)
            }
            list.add(appInfo)
            it[VL_SELECTED_APPS] = Gson().toJson(list)
        }
    }

    override suspend fun removeApp(appInfo: AppInfo) {
        context.dataStore.edit {
            val json = it[VL_SELECTED_APPS] ?: return@edit
            val list = Gson().toObject<MutableList<AppInfo>>(json)
            list.remove(appInfo)
            it[VL_SELECTED_APPS] = Gson().toJson(list)
        }
    }

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

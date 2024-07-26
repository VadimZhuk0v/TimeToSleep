package com.vadmax.timetosleep.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.vadmax.core.data.AppInfo
import com.vadmax.core.data.RingerMode
import com.vadmax.core.utils.extentions.safeValueOf
import com.vadmax.core.utils.extentions.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val SHARED_NAME = "shared"

private val VL_IS_TIMER_ENABLED = booleanPreferencesKey("VL_SKIP_TUTORIAL")
private val VL_IS_LOCK_SCREEN_ENABLE = booleanPreferencesKey("VL_IS_LOCK_SCREEN_ENABLE")
private val VL_IS_WIFI_ENABLE = booleanPreferencesKey("VL_IS_WIFI_ENABLE")
private val VL_IS_BL_ENABLE = booleanPreferencesKey("VL_IS_BL_ENABLE")
private val VL_IS_FIRST_TIME = booleanPreferencesKey("VL_IS_FIRST_TIME")
private val VL_IS_VIBRATION_ENABLE = booleanPreferencesKey("VL_IS_VIBRATION_ENABLE")
private val VL_SELECTED_TIME = longPreferencesKey("VL_SELECTED_TIME")
private val VL_SELECTED_APPS = stringPreferencesKey("VL_SELECTED_APPS")
private val VL_RINGER_MODE = stringPreferencesKey("VL_RINGER_MODE")
private val VL_ENABLE_TIMER_COUNTER = intPreferencesKey("VL_ENABLE_TIMER_COUNTER")
private val VL_SOUND_EFFECT = booleanPreferencesKey("VL_SOUND_EFFECT")

interface SettingsProvider {

    val isTimerEnabled: StateFlow<Boolean>
    val isLockScreenEnable: Flow<Boolean>
    val isFirstTime: Flow<Boolean>
    val isVibrationEnable: StateFlow<Boolean>
    val isDisableWifiEnable: Flow<Boolean>
    val isDisableBluetoothEnable: Flow<Boolean>
    val selectedAppsFlow: Flow<List<AppInfo>>
    val enableTimerCounter: Flow<Int>
    val ringerMode: Flow<RingerMode?>
    val soundEffect: StateFlow<Boolean>

    suspend fun setTimerEnable(value: Boolean)

    suspend fun setDisableWifiEnable(value: Boolean)

    suspend fun setVibrationEnable(value: Boolean)

    suspend fun setDisableBluetoothEnable(value: Boolean)

    suspend fun setLockScreenEnable(value: Boolean)

    suspend fun setRingerMode(mode: RingerMode?)

    suspend fun incEnableTimerCounter()

    suspend fun setSelectedTime(time: Long?)

    suspend fun getSelectedTime(): Long?

    suspend fun selectApp(appInfo: AppInfo)

    suspend fun removeApp(appInfo: AppInfo)

    suspend fun setSoundEffect(value: Boolean)
}

private val Context.dataStore by preferencesDataStore(name = SHARED_NAME)

internal class SettingsProviderImpl(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : SettingsProvider {

    override val isTimerEnabled = context.dataStore.data.map {
        it[VL_IS_TIMER_ENABLED] ?: false
    }.stateIn(coroutineScope, SharingStarted.Eagerly, false)

    override val isLockScreenEnable = context.dataStore.data.map {
        it[VL_IS_LOCK_SCREEN_ENABLE] ?: false
    }

    override val isVibrationEnable = context.dataStore.data.map {
        it[VL_IS_VIBRATION_ENABLE] ?: true
    }.stateIn(coroutineScope, SharingStarted.Eagerly, false)

    override val isDisableWifiEnable = context.dataStore.data.map {
        it[VL_IS_WIFI_ENABLE] ?: false
    }

    override val isDisableBluetoothEnable = context.dataStore.data.map {
        it[VL_IS_BL_ENABLE] ?: false
    }

    override val ringerMode = context.dataStore.data.map {
        val modeString = it[VL_RINGER_MODE]
        safeValueOf<RingerMode>(modeString)
    }

    override val isFirstTime = context.dataStore.data.map {
        it[VL_IS_FIRST_TIME] ?: true
    }

    override val enableTimerCounter = context.dataStore.data.map {
        it[VL_ENABLE_TIMER_COUNTER] ?: 1
    }

    override val soundEffect = context.dataStore.data.map {
        it[VL_SOUND_EFFECT] ?: true
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), true)

    override suspend fun incEnableTimerCounter() {
        context.dataStore.edit {
            val value = it[VL_ENABLE_TIMER_COUNTER] ?: 1
            it[VL_ENABLE_TIMER_COUNTER] = value + 1
        }
    }

    override suspend fun setVibrationEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_VIBRATION_ENABLE] = value
        }
    }

    override suspend fun setRingerMode(mode: RingerMode?) {
        context.dataStore.edit {
            it[VL_RINGER_MODE] = mode?.name ?: ""
        }
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

    override suspend fun setLockScreenEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_LOCK_SCREEN_ENABLE] = value
        }
    }

    override suspend fun setDisableWifiEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_WIFI_ENABLE] = value
        }
    }

    override suspend fun setDisableBluetoothEnable(value: Boolean) {
        context.dataStore.edit {
            it[VL_IS_BL_ENABLE] = value
        }
    }

    override suspend fun setSoundEffect(value: Boolean) {
        context.dataStore.edit {
            it[VL_SOUND_EFFECT] = value
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

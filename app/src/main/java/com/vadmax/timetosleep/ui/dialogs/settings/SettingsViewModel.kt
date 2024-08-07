package com.vadmax.timetosleep.ui.dialogs.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.domain.usercases.GetRingerMode
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.IsLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.SetLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.SetRingerMode
import com.vadmax.timetosleep.domain.usercases.SetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.SetVibrationEnable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressWarnings("LongParameterList")
class SettingsViewModel(
    isLockScreenEnable: IsLockScreenEnable,
    isDisableBluetoothEnable: IsDisableBluetoothEnable,
    getRingerMode: GetRingerMode,
    isVibrationEnable: IsVibrationEnable,
    private val setLockScreenEnable: SetLockScreenEnable,
    private val setDisableBluetoothEnable: SetDisableBluetoothEnable,
    private val setRingerMode: SetRingerMode,
    private val setVibrationEnable: SetVibrationEnable,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val setSoundEffectEnable: SetSoundEffectEnable,
) : ViewModel() {

    val lockScreenEnable = isLockScreenEnable()
    val disableBluetoothEnable = isDisableBluetoothEnable()
    val ringerMode = getRingerMode()
    val vibrationEnable = isVibrationEnable()
    val soundEffectEnable = getSoundEffectEnable()

    fun setLockScreenEnable(value: Boolean) {
        Timber.i("👆 Lock screen enable click:$value")
        viewModelScope.launch(Dispatchers.IO) {
            setLockScreenEnable.invoke(value)
        }
    }

    fun setSoundEffectEnable(value: Boolean) {
        Timber.i("👆 Sound effect enable click: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setSoundEffectEnable.invoke(value)
        }
    }

    fun setDisableBluetoothEnable(value: Boolean) {
        Timber.i("👆 Disable bluetooth click: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setDisableBluetoothEnable.invoke(value)
        }
    }

    fun setVibrationEnable(value: Boolean) {
        Timber.i("👆 Vibration enable click: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setVibrationEnable.invoke(value)
        }
    }

    fun setRingerMode(ringerMode: RingerMode?) {
        Timber.i("👆 Ringer mode click: $ringerMode")
        viewModelScope.launch(Dispatchers.IO) {
            setRingerMode.invoke(ringerMode)
        }
    }
}

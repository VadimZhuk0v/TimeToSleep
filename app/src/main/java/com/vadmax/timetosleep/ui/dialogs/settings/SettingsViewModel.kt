package com.vadmax.timetosleep.ui.dialogs.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.data.RingerMode
import com.vadmax.core.log
import com.vadmax.timetosleep.domain.usercases.GetRingerMode
import com.vadmax.timetosleep.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.IsLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.SetLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.SetRingerMode
import com.vadmax.timetosleep.domain.usercases.SetVibrationEnable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
) : ViewModel() {

    val lockScreenEnable = isLockScreenEnable()
    val disableBluetoothEnable = isDisableBluetoothEnable()
    val ringerMode = getRingerMode()
    val vibrationEnable = isVibrationEnable()

    fun setLockScreenEnable(value: Boolean) {
        log.d("Lock screen enable:$value")
        viewModelScope.launch(Dispatchers.IO) {
            setLockScreenEnable.invoke(value)
        }
    }

    fun setDisableBluetoothEnable(value: Boolean) {
        log.d("Disable bluetooth: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setDisableBluetoothEnable.invoke(value)
        }
    }

    fun setVibrationEnable(value: Boolean) {
        log.d("Vibration enable: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setVibrationEnable.invoke(value)
        }
    }

    fun setRingerMode(ringerMode: RingerMode?) {
        log.d("Ringer mode: $ringerMode")
        viewModelScope.launch(Dispatchers.IO) {
            setRingerMode.invoke(ringerMode)
        }
    }
}

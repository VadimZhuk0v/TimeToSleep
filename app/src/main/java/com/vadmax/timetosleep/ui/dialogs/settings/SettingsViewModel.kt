package com.vadmax.timetosleep.ui.dialogs.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vadmax.io.data.RingerMode
import com.vadmax.io.domain.usercases.GetRingerMode
import com.vadmax.io.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.io.domain.usercases.IsLockScreenEnable
import com.vadmax.io.domain.usercases.IsVibrationEnable
import com.vadmax.io.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.io.domain.usercases.SetLockScreenEnable
import com.vadmax.io.domain.usercases.SetRingerMode
import com.vadmax.io.domain.usercases.SetVibrationEnable
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
) : ViewModel() {

    val lockScreenEnable = isLockScreenEnable().asLiveData()
    val disableBluetoothEnable = isDisableBluetoothEnable().asLiveData()
    val ringerMode = getRingerMode().asLiveData()
    val vibrationEnable = isVibrationEnable().asLiveData()

    fun setLockScreenEnable(value: Boolean) {
        Timber.d("Lock screen enable:$value")
        viewModelScope.launch(Dispatchers.IO) {
            setLockScreenEnable.invoke(value)
        }
    }

    fun setDisableBluetoothEnable(value: Boolean) {
        Timber.d("Disable bluetooth: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setDisableBluetoothEnable.invoke(value)
        }
    }

    fun setVibrationEnable(value: Boolean) {
        Timber.d("Vibration enable: $value")
        viewModelScope.launch(Dispatchers.IO) {
            setVibrationEnable.invoke(value)
        }
    }

    fun setRingerMode(ringerMode: RingerMode?) {
        Timber.d("Ringer mode: $ringerMode")
        viewModelScope.launch(Dispatchers.IO) {
            setRingerMode.invoke(ringerMode)
        }
    }
}

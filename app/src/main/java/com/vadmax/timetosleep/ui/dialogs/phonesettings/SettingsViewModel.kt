package com.vadmax.timetosleep.ui.dialogs.phonesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.domain.usercases.local.GetOpenSourceLink
import com.vadmax.timetosleep.domain.usercases.local.GetRingerMode
import com.vadmax.timetosleep.domain.usercases.local.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.local.IsDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.local.IsLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.local.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.local.SetDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.local.SetLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.local.SetRingerMode
import com.vadmax.timetosleep.domain.usercases.local.SetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.local.SetVibrationEnable
import com.vadmax.timetosleep.ui.dialogs.phonesettings.support.PhoneSettingsEvent
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@SuppressWarnings("LongParameterList")
@KoinViewModel
class SettingsViewModel(
    isLockScreenEnable: IsLockScreenEnable,
    isDisableBluetoothEnable: IsDisableBluetoothEnable,
    getRingerMode: GetRingerMode,
    isVibrationEnable: IsVibrationEnable,
    getSoundEffectEnable: GetSoundEffectEnable,
    private val setLockScreenEnable: SetLockScreenEnable,
    private val setDisableBluetoothEnable: SetDisableBluetoothEnable,
    private val setRingerMode: SetRingerMode,
    private val setVibrationEnable: SetVibrationEnable,
    private val setSoundEffectEnable: SetSoundEffectEnable,
    private val getOpenSourceLink: GetOpenSourceLink,
) : ViewModel() {

    val lockScreenEnable = isLockScreenEnable()
    val disableBluetoothEnable = isDisableBluetoothEnable()
    val ringerMode = getRingerMode()
    val vibrationEnable = isVibrationEnable()
    val soundEffectEnable = getSoundEffectEnable()

    private val _event = MutableEventFlow<PhoneSettingsEvent>()
    val event: EventFlow<PhoneSettingsEvent> = _event

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

    fun onOpenSourceClick() {
        Timber.i("👆 Open source click")
        _event.tryEmit(PhoneSettingsEvent.OpenLink(getOpenSourceLink()))
    }
}

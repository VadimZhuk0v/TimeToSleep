package com.vadmax.timetosleep.ui.screens.pctimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.map
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.repositories.pc.PCRepository
import com.vadmax.timetosleep.domain.repositories.pc.TimerState
import com.vadmax.timetosleep.domain.usercases.local.DeleteServerConfig
import com.vadmax.timetosleep.domain.usercases.local.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.local.IsVibrationEnable
import com.vadmax.timetosleep.ui.screens.pctimer.ui.PCTimerScreenState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCTimerViewModel(
    isVibrationEnable: IsVibrationEnable,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val pcRepository: PCRepository,
    private val deleteServerConfig: DeleteServerConfig,
) : ViewModel() {

    val vibrationEnable = isVibrationEnable()
    val selectTime = pcRepository.selectTime
    val connected = pcRepository.connected

    val timerEnable = pcRepository.enabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        false,
    )

    val screenState = pcRepository.timerState.map(viewModelScope) {
        when (it) {
            TimerState.NoDevice -> PCTimerScreenState.NoDeice
            TimerState.Idle -> PCTimerScreenState.Idle
            is TimerState.Timer -> PCTimerScreenState.Timer(it.initialTime)
        }
    }

    fun setTimerEnable(value: Boolean) {
        Timber.i("👆 On enable click")
        pcRepository.setEnabled(value)
    }

    fun attachScope() {
        Timber.d("Attach viewmodel scope to pcRepository")
        pcRepository.attachScope(viewModelScope)
    }

    fun detachScope() {
        Timber.d("Detach viewmodel scope from pcRepository")
        pcRepository.detachScope()
    }

    fun setTime(time: TimeUIModel) {
        viewModelScope.launch {
            pcRepository.changeTimeByUser(time)
        }
    }

    fun setServerConfig(data: String) {
        Timber.d("QR data: $data")
        pcRepository.setServerConfig(data)
    }

    fun onUnpairClick() {
        Timber.i("👆 On unpair click")
        viewModelScope.launch {
            deleteServerConfig.invoke()
        }
    }
}

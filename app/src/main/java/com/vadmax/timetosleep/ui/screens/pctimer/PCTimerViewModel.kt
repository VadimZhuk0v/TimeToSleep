package com.vadmax.timetosleep.ui.screens.pctimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.analytics.AppAnalytics
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.repositories.pc.PCRepository
import com.vadmax.timetosleep.domain.repositories.pc.PCTimerRepositoryEvent
import com.vadmax.timetosleep.domain.repositories.pc.TimerState
import com.vadmax.timetosleep.domain.usercases.local.DeleteServerConfig
import com.vadmax.timetosleep.domain.usercases.local.IsVibrationEnable
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenDialog
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenEvent
import com.vadmax.timetosleep.ui.screens.pctimer.ui.PCTimerScreenState
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCTimerViewModel(
    isVibrationEnable: IsVibrationEnable,
    private val pcRepository: PCRepository,
    private val deleteServerConfig: DeleteServerConfig,
    private val analytics: AppAnalytics,
) : ViewModel() {

    val vibrationEnable = isVibrationEnable()
    val selectTime = pcRepository.selectTime
    val connected = pcRepository.connected

    private val _event = MutableEventFlow<PCTimerScreenEvent>()
    val event: EventFlow<PCTimerScreenEvent> = _event

    private val _dialog = MutableEventFlow<PCTimerScreenDialog>()
    val dialog: EventFlow<PCTimerScreenDialog> = _dialog

    val timerEnable = pcRepository.enabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(500),
        false,
    )

    val screenState = pcRepository.timerState.map {
        when (it) {
            TimerState.NoDevice -> PCTimerScreenState.NoDeice
            TimerState.Idle -> PCTimerScreenState.Idle
            is TimerState.Timer -> PCTimerScreenState.Timer(it.initialTime)
        }
    }

    init {
        collectPCTimerRepositoryEvents()
    }

    fun setTimerEnable(value: Boolean) {
        Timber.i("👆 On enable click")
        analytics.userEnablePCTimer(value)
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

    fun onInfoClick() {
        Timber.i("👆 On info click")
        analytics.infoPC()
        _dialog.tryEmit(PCTimerScreenDialog.Info)
    }

    fun onSettingsClick() {
        Timber.i("👆 On settings click")
        analytics.settingsPC()
        _dialog.tryEmit(PCTimerScreenDialog.Settings)
    }

    fun onUnpairClick() {
        Timber.i("👆 On unpair click")
        analytics.unpairPC()
        viewModelScope.launch {
            deleteServerConfig.invoke()
        }
    }

    fun onTurnOffClick() {
        Timber.i("👆 On turn off click")
        analytics.turnOffPC()
        _dialog.tryEmit(PCTimerScreenDialog.TurnOff)
    }

    fun onConfirmTurnOffClick() {
        Timber.i("👆 On confirm turn off click")
        analytics.turnOffConfirm()
        pcRepository.turnOff()
    }

    private fun collectPCTimerRepositoryEvents() {
        viewModelScope.launch {
            pcRepository.event.collect {
                val event = when (it) {
                    PCTimerRepositoryEvent.UnsupportedQR -> PCTimerScreenEvent.UnsupportedQR
                }
                _event.emit(event)
            }
        }
    }
}

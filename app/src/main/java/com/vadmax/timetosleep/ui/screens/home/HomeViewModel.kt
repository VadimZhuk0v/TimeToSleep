package com.vadmax.timetosleep.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.domain.usercases.CancelApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.IncEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.IsTimerEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.ScheduleApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.SetSelectedTime
import com.vadmax.timetosleep.domain.usercases.SetTimerEnable
import com.vadmax.timetosleep.domain.usercases.remote.ShutdownRemote
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenEvent
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@SuppressWarnings("LongParameterList")
class HomeViewModel(
    isTimerEnable: IsTimerEnable,
    isVibrationEnable: IsVibrationEnable,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val setTimerActive: SetTimerEnable,
    private val setSelectedTime: SetSelectedTime,
    private val getSelectedTime: GetSelectedTime,
    private val scheduleApplyActionsWorker: ScheduleApplyActionsWorker,
    private val incEnableTimerCounter: IncEnableTimerCounter,
    private val cancelApplyActionsWorker: CancelApplyActionsWorker,
    private val shutdownRemote: ShutdownRemote,
) : ViewModel() {

    private val _event = MutableEventFlow<HomeScreenEvent>()
    val event: EventFlow<HomeScreenEvent> = _event

    val timerEnable = isTimerEnable()
    val vibrationEnable = isVibrationEnable()

    fun setTimerEnable(isEnable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (getSoundEffectEnable().value) {
                _event.emit(HomeScreenEvent.RippleSound)
            }
            setTimerActive(isEnable)
            if (isEnable) {
                incEnableTimerCounter()
                scheduleApplyActionsWorker()
            } else {
                cancelApplyActionsWorker()
            }
        }
    }

    suspend fun getInitialTime() = getSelectedTime()

    fun setTime(
        hour: Int,
        minute: Int,
    ) {
        val calendar = Calendar.getInstance().apply {
            this.hour = hour
            this.minute = minute
            second = 0
            if (time < Date()) {
                this.add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            setSelectedTime(calendar.timeInMillis)
            if (timerEnable.value) {
                scheduleApplyActionsWorker()
            }
        }
    }

    fun shutdown() {
        viewModelScope.launch {
            shutdownRemote()
        }
    }
}

package com.vadmax.timetosleep.ui.screens.phonetimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.domain.usecases.CancelApplyActionsWorker
import com.vadmax.timetosleep.domain.usecases.ScheduleApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.IncEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.IsTimerEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.SetSelectedTime
import com.vadmax.timetosleep.domain.usercases.SetTimerEnable
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenEvent
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.util.Calendar
import java.util.Date

@KoinViewModel
class PhoneTimerViewModel(
    isTimerEnable: IsTimerEnable,
    isVibrationEnable: IsVibrationEnable,
    getSelectedTime: GetSelectedTime,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val setTimerActive: SetTimerEnable,
    private val setSelectedTime: SetSelectedTime,
    private val scheduleApplyActionsWorker: ScheduleApplyActionsWorker,
    private val incEnableTimerCounter: IncEnableTimerCounter,
    private val cancelApplyActionsWorker: CancelApplyActionsWorker,
) : ViewModel() {

    private val _event = MutableEventFlow<PhoneTimerScreenEvent>()
    val event: EventFlow<PhoneTimerScreenEvent> = _event

    val timerEnable = isTimerEnable()
    val vibrationEnable = isVibrationEnable()
    val initialTime = flow {
        val time = getSelectedTime()?.run { Date(this) }
        emit(time ?: Date())
    }

    fun setTimerEnable(isEnable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (getSoundEffectEnable().value) {
                _event.emit(PhoneTimerScreenEvent.RippleSound)
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
}

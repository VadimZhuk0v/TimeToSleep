package com.vadmax.timetosleep.ui.screens.phonetimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.usecases.CancelApplyActionsWorker
import com.vadmax.timetosleep.domain.usecases.ScheduleApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.local.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.local.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.local.IncEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.local.IsTimerEnable
import com.vadmax.timetosleep.domain.usercases.local.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.local.SetSelectedTime
import com.vadmax.timetosleep.domain.usercases.local.SetTimerEnable
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenEvent
import com.vadmax.timetosleep.ui.screens.phonetimer.ui.PhoneTimerScreenState
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.util.Calendar
import java.util.Date

@KoinViewModel
class PhoneTimerViewModel(
    isTimerEnable: IsTimerEnable,
    isVibrationEnable: IsVibrationEnable,
    private val getSelectedTime: GetSelectedTime,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val setTimerActive: SetTimerEnable,
    private val setSelectedTime: SetSelectedTime,
    private val scheduleApplyActionsWorker: ScheduleApplyActionsWorker,
    private val incEnableTimerCounter: IncEnableTimerCounter,
    private val cancelApplyActionsWorker: CancelApplyActionsWorker,
) : ViewModel() {

    private val _event = MutableEventFlow<PhoneTimerScreenEvent>()
    val event: EventFlow<PhoneTimerScreenEvent> = _event

    private val _screenState = MutableStateFlow<PhoneTimerScreenState>(PhoneTimerScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    val timerEnable = isTimerEnable()
    val vibrationEnable = isVibrationEnable()

    init {
        setUpInitialTime()
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

    fun setTime(time: TimeUIModel) {
        val calendar = Calendar.getInstance().apply {
            this.hour = time.hours
            this.minute = time.minutes
            second = 0
            if (this.time < Date()) {
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

    private fun setUpInitialTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val date = getSelectedTime()?.run { Date(this) } ?: Date()
            val time = Calendar.getInstance().apply {
                this.time = date
            }
            _screenState.value = PhoneTimerScreenState.Timer(TimeUIModel(time.hour, time.minute))
        }
    }
}

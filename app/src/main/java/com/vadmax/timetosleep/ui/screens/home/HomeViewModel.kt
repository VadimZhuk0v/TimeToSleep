package com.vadmax.timetosleep.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.domain.usercases.GetEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.IncEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.IsTimerEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivation
import com.vadmax.timetosleep.domain.usercases.SetSelectedTime
import com.vadmax.timetosleep.domain.usercases.SetTimerEnable
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressWarnings("LongParameterList")
class HomeViewModel(
    isTimerEnable: IsTimerEnable,
    getEnableTimerCounter: GetEnableTimerCounter,
    isVibrationEnable: IsVibrationEnable,
    private val setTimerActive: SetTimerEnable,
    private val setSelectedTime: SetSelectedTime,
    private val getSelectedTime: GetSelectedTime,
    private val setAlarmActivation: SetAlarmActivation,
    private val incEnableTimerCounter: IncEnableTimerCounter,
) : ViewModel() {

    val timerEnable = isTimerEnable()
    val enableTimerCounter = getEnableTimerCounter()
    val vibrationEnable = isVibrationEnable()

    fun setTimerEnable(isEnable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setTimerActive(isEnable)
            setAlarmActivation()
            if (isEnable) {
                incEnableTimerCounter()
            }
        }
    }

    suspend fun getInitialTime() = getSelectedTime()

    fun setTime(hour: Int, minute: Int) {
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
            setAlarmActivation()
        }
    }
}

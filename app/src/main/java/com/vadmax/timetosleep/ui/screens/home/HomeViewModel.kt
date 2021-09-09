package com.vadmax.timetosleep.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.io.domain.usercases.GetSelectedTime
import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.io.domain.usercases.SetSelectedTime
import com.vadmax.io.domain.usercases.SetTimerEnable
import com.vadmax.timetosleep.domain.usercases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class HomeViewModel(
    isTimerEnable: IsTimerEnable,
    private val setTimerActive: SetTimerEnable,
    private val setSelectedTime: SetSelectedTime,
    private val getSelectedTime: GetSelectedTime,
    private val setAlarmActivation: SetAlarmActivation,
    private val applyActions: ApplyActions,
) : ViewModel() {

    val timerEnable = isTimerEnable().asLiveData()

    fun setTimerEnable(isEnable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
//            applyActions()
            setTimerActive(isEnable)
        }
    }

    suspend fun getInitialTime() = getSelectedTime()

    fun setTime(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            this.hour = hour
            this.minute = minute
            second = 0
            if (time < Date()) {
                this.add(Calendar.DAY_OF_YEAR, +1)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            setSelectedTime(calendar.timeInMillis)
            setAlarmActivation()
        }
    }
}
package com.vadmax.timetosleep.ui.screens.pctimer

import androidx.lifecycle.ViewModel
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.koin.android.annotation.KoinViewModel
import java.util.Calendar
import java.util.Date

@KoinViewModel
class PCTimerViewModel(
    isVibrationEnable: IsVibrationEnable,
    private val getSoundEffectEnable: GetSoundEffectEnable,
) : ViewModel() {

    val vibrationEnable = isVibrationEnable()
    val initialTime = flow { emit(Date()) }

    val timerEnable = MutableStateFlow(false)

    fun setTimerEnable(isEnable: Boolean) {
        timerEnable.value = isEnable
    }

//    suspend fun getInitialTime() = getSelectedTime()

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
    }
}

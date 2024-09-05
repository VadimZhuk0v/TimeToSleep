package com.vadmax.timetosleep.ui.screens.pctimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.core.utils.extentions.second
import com.vadmax.timetosleep.domain.repositories.pc.PCRepository
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber
import java.util.Calendar
import java.util.Date

@KoinViewModel
class PCTimerViewModel(
    isVibrationEnable: IsVibrationEnable,
    private val getSoundEffectEnable: GetSoundEffectEnable,
    private val pcRepository: PCRepository,
) : ViewModel() {

    val vibrationEnable = isVibrationEnable()
    val initialTime = flow { emit(Date()) }

    val timerEnable = MutableStateFlow(false)

    init {
        pcRepository.attachScope(viewModelScope)
    }

    fun setTimerEnable(isEnable: Boolean) {
        timerEnable.value = isEnable
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            },
        ) {
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
    }
}

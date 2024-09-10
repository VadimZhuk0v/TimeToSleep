package com.vadmax.timetosleep.ui.screens.phonetimer.ui

import androidx.compose.runtime.Stable
import com.vadmax.timetosleep.data.TimeUIModel

@Stable
sealed interface PhoneTimerScreenState {

    data object Idle : PhoneTimerScreenState

    data class Timer(val initialTime: TimeUIModel) : PhoneTimerScreenState
}

package com.vadmax.timetosleep.ui.screens.pctimer.ui

import androidx.compose.runtime.Stable
import com.vadmax.timetosleep.data.TimeUIModel

@Stable
sealed interface PCTimerScreenState {

    data object Idle : PCTimerScreenState

    data object NoDeice : PCTimerScreenState

    data class Timer(val initialTime: TimeUIModel) : PCTimerScreenState
}

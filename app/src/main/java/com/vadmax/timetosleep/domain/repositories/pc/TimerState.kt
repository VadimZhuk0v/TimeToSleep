package com.vadmax.timetosleep.domain.repositories.pc

import com.vadmax.timetosleep.data.TimeUIModel

sealed interface TimerState {

    data object Idle : TimerState

    data class Timer(val initialTime: TimeUIModel) : TimerState
}

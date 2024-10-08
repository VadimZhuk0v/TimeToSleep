package com.vadmax.timetosleep.domain.repositories.pc

import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.utils.flow.EventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PCRepository {

    val event: Flow<PCTimerRepositoryEvent>

    val timerState: Flow<TimerState>

    val connected: StateFlow<Boolean>

    val selectTime: EventFlow<TimeUIModel>

    val enabled: Flow<Boolean>

    fun attachScope(coroutineScope: CoroutineScope)

    fun detachScope()

    suspend fun changeTimeByUser(time: TimeUIModel)

    fun setEnabled(value: Boolean)

    fun setServerConfig(data: String)

    fun turnOff()
}

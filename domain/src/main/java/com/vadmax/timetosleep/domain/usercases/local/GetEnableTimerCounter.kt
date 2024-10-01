package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetEnableTimerCounter {
    operator fun invoke(): Flow<Int>
}

@Factory(binds = [GetEnableTimerCounter::class])
internal class GetEnableTimerCounterImpl(private val settingsProvider: SettingsProvider) :
    GetEnableTimerCounter {

    override fun invoke() = settingsProvider.enableTimerCounter
}

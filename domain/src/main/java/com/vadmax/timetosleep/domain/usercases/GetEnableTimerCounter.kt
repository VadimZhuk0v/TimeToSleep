package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetEnableTimerCounter {
    operator fun invoke(): Flow<Int>
}

internal class GetEnableTimerCounterImpl(
    private val settingsProvider: SettingsProvider,
) : GetEnableTimerCounter {

    override fun invoke() = settingsProvider.enableTimerCounter
}

package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetEnableTimerCounter {
    operator fun invoke(): Flow<Int>
}

class GetEnableTimerCounterImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : GetEnableTimerCounter {

    override fun invoke() = settingsProvider.enableTimerCounter
}

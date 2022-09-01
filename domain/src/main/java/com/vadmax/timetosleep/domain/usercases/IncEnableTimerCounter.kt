package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface IncEnableTimerCounter {
    suspend operator fun invoke()
}

internal class IncEnableTimerCounterImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : IncEnableTimerCounter {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.incEnableTimerCounter()
    }
}

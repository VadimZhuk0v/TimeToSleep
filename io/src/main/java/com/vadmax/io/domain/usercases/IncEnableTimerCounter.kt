package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface IncEnableTimerCounter {
    suspend operator fun invoke()
}

class IncEnableTimerCounterImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : IncEnableTimerCounter {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.incEnableTimerCounter()
    }
}

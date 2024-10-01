package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface IncEnableTimerCounter {
    suspend operator fun invoke()
}

@Factory(binds = [IncEnableTimerCounter::class])
internal class IncEnableTimerCounterImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : IncEnableTimerCounter {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.incEnableTimerCounter()
    }
}

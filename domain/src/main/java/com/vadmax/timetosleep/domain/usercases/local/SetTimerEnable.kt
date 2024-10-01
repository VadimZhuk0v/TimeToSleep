package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetTimerEnable {
    suspend operator fun invoke(value: Boolean)
}

@Factory(binds = [SetTimerEnable::class])
internal class SetTimerEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetTimerEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setTimerEnable(value)
    }
}

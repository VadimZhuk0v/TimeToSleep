package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetVibrationEnable {
    suspend operator fun invoke(value: Boolean)
}

internal class SetVibrationEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetVibrationEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setVibrationEnable(value)
    }
}

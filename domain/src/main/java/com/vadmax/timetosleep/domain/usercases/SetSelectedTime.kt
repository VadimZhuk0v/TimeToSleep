package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetSelectedTime {
    suspend operator fun invoke(time: Long?)
}

internal class SetSelectedTimeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetSelectedTime {

    override suspend fun invoke(time: Long?) = withContext(dispatcher) {
        settingsProvider.setSelectedTime(time)
    }
}

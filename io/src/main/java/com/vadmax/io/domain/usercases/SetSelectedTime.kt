package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetSelectedTime {
    suspend operator fun invoke(time: Long?)
}

class SetSelectedTimeImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : SetSelectedTime {

    override suspend fun invoke(time: Long?) = withContext(dispatcher) {
        settingsProvider.setSelectedTime(time)
    }
}

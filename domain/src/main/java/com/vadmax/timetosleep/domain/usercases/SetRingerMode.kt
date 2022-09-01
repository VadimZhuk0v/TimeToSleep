package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetRingerMode {
    suspend operator fun invoke(mode: RingerMode?)
}

internal class SetRingerModeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetRingerMode {

    override suspend fun invoke(mode: RingerMode?) = withContext(dispatcher) {
        settingsProvider.setRingerMode(mode)
    }
}

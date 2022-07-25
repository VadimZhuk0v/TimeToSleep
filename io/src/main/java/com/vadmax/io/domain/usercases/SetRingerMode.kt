package com.vadmax.io.domain.usercases

import com.vadmax.io.data.RingerMode
import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetRingerMode {
    suspend operator fun invoke(mode: RingerMode?)
}

class SetRingerModeImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : SetRingerMode {

    override suspend fun invoke(mode: RingerMode?) = withContext(dispatcher) {
        settingsProvider.setRingerMode(mode)
    }
}

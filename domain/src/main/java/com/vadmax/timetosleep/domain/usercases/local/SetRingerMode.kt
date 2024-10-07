package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetRingerMode {
    suspend operator fun invoke(mode: RingerMode)
}

@Factory(binds = [SetRingerMode::class])
internal class SetRingerModeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetRingerMode {

    override suspend fun invoke(mode: RingerMode) = withContext(dispatcher) {
        settingsProvider.setRingerMode(mode)
    }
}

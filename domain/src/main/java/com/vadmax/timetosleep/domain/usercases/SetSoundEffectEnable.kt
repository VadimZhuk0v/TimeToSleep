package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetSoundEffectEnable {
    suspend operator fun invoke(value: Boolean)
}

@Factory(binds = [SetSoundEffectEnable::class])
internal class SetSoundEffectEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SetSoundEffectEnable {

    override suspend fun invoke(value: Boolean) = withContext(coroutineContext) {
        settingsProvider.setSoundEffect(value)
    }
}

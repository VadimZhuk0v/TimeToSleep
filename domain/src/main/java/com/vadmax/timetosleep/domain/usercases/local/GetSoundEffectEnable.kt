package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

fun interface GetSoundEffectEnable {

    operator fun invoke(): StateFlow<Boolean>
}

@Factory(binds = [com.vadmax.timetosleep.domain.usercases.local.GetSoundEffectEnable::class])
internal class GetSoundEffectEnableImpl(private val settingsProvider: SettingsProvider) :
    com.vadmax.timetosleep.domain.usercases.local.GetSoundEffectEnable {

    override fun invoke() = settingsProvider.soundEffect
}

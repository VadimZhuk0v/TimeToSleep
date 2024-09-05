package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

fun interface GetSoundEffectEnable {

    operator fun invoke(): StateFlow<Boolean>
}

@Factory(binds = [GetSoundEffectEnable::class])
internal class GetSoundEffectEnableImpl(private val settingsProvider: SettingsProvider) :
    GetSoundEffectEnable {

    override fun invoke() = settingsProvider.soundEffect
}

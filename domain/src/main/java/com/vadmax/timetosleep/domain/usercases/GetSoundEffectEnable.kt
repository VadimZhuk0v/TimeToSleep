package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun interface GetSoundEffectEnable {

    operator fun invoke(): StateFlow<Boolean>
}

internal class GetSoundEffectEnableImpl(private val settingsProvider: SettingsProvider) :
    GetSoundEffectEnable {

    override fun invoke() = settingsProvider.soundEffect
}

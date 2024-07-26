package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.StateFlow

fun interface IsTimerEnable {
    operator fun invoke(): StateFlow<Boolean>
}

internal class IsTimerEnableImpl(private val settingsProvider: SettingsProvider) : IsTimerEnable {

    override fun invoke() = settingsProvider.isTimerEnabled
}

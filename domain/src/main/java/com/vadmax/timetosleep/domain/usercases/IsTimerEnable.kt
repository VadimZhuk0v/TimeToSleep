package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsTimerEnable {
    operator fun invoke(): Flow<Boolean>
}

internal class IsTimerEnableImpl(
    private val settingsProvider: SettingsProvider,
) : IsTimerEnable {

    override fun invoke() = settingsProvider.isTimerEnabled
}

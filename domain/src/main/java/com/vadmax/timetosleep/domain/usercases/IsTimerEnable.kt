package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

fun interface IsTimerEnable {
    operator fun invoke(): StateFlow<Boolean>
}

@Factory(binds = [IsTimerEnable::class])
internal class IsTimerEnableImpl(private val settingsProvider: SettingsProvider) : IsTimerEnable {

    override fun invoke() = settingsProvider.isTimerEnabled
}

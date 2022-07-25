package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsTimerEnable {
    operator fun invoke(): Flow<Boolean>
}

class IsTimerEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsTimerEnable {

    override fun invoke() = settingsProvider.isTimerEnabled
}

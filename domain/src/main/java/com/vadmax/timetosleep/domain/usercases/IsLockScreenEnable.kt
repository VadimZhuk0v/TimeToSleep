package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsLockScreenEnable {
    operator fun invoke(): Flow<Boolean>
}

internal class IsLockScreenEnableImpl(
    private val settingsProvider: SettingsProvider,
) : IsLockScreenEnable {

    override fun invoke() = settingsProvider.isLockScreenEnable
}

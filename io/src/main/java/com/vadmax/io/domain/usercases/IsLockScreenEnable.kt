package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsLockScreenEnable {
    operator fun invoke(): Flow<Boolean>
}

class IsLockScreenEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsLockScreenEnable {

    override fun invoke() = settingsProvider.isLockScreenEnable
}

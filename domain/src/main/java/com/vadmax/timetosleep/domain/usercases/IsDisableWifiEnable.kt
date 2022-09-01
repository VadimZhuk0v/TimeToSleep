package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsDisableWifiEnable {
    operator fun invoke(): Flow<Boolean>
}

internal class IsDisableWifiEnableImpl(
    private val settingsProvider: SettingsProvider,
) : IsDisableWifiEnable {

    override fun invoke() = settingsProvider.isDisableWifiEnable
}

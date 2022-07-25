package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsDisableWifiEnable {
    operator fun invoke(): Flow<Boolean>
}

class IsDisableWifiEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsDisableWifiEnable {

    override fun invoke() = settingsProvider.isDisableWifiEnable
}

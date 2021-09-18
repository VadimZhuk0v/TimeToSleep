package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class IsDisableWifiEnable(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.isDisableWifiEnable
}

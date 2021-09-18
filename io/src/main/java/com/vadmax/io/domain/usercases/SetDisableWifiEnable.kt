package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class SetDisableWifiEnable(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(value: Boolean) {
        settingsProvider.setDisableWifiEnable(value)
    }
}

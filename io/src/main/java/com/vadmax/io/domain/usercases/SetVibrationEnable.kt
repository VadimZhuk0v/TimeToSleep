package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class SetVibrationEnable(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(value: Boolean) {
        settingsProvider.setVibrationEnable(value)
    }
}

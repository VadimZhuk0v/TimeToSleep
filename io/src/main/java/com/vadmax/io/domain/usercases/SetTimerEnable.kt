package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class SetTimerEnable(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(value: Boolean) {
        settingsProvider.setTimerEnable(value)
    }
}

package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class SetLockScreenEnable(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(value: Boolean) {
        settingsProvider.setLockScreenEnable(value)
    }
}

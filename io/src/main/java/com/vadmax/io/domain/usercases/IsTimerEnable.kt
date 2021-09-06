package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class IsTimerEnable(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.isTimerEnabled
}

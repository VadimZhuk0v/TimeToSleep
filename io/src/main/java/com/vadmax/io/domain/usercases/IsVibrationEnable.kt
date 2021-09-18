package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class IsVibrationEnable(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.isVibrationEnable
}

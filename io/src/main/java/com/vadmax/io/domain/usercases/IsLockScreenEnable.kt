package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class IsLockScreenEnable(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.isLockScreenEnable
}

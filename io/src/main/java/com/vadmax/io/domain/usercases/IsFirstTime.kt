package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class IsFirstTime(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.isFirstTime
}

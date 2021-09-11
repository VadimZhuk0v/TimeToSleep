package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class GetEnableTimerCounter(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.enableTimerCounter
}

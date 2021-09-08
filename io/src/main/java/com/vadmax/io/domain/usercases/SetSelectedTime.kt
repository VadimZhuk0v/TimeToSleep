package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class SetSelectedTime(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(time: Long?) {
        settingsProvider.setSelectedTime(time)
    }
}

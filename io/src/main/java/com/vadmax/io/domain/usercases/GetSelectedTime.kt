package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class GetSelectedTime(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke() = settingsProvider.getSelectedTime()
}

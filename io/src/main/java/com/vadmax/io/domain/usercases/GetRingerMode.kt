package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class GetRingerMode(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.ringerMode
}

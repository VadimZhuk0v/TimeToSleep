package com.vadmax.io.domain.usercases

import com.vadmax.io.data.RingerMode
import com.vadmax.io.data.SettingsProvider

class SetRingerMode(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(mode: RingerMode?) {
        settingsProvider.setRingerMode(mode)
    }
}

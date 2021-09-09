package com.vadmax.io.domain.usercases

import com.vadmax.io.data.AppInfo
import com.vadmax.io.data.SettingsProvider

class SelectApp(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(appInfo: AppInfo) {
        settingsProvider.selectApp(appInfo)
    }
}

package com.vadmax.io.domain.usercases

import com.vadmax.io.data.AppInfo
import com.vadmax.io.data.SettingsProvider

class UnselectApp(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(appInfo: AppInfo) {
        settingsProvider.removeApp(appInfo)
    }
}

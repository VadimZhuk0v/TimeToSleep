package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider

class GetSelectedApps(private val settingsProvider: SettingsProvider) {

    operator fun invoke() = settingsProvider.selectedAppsFlow
}

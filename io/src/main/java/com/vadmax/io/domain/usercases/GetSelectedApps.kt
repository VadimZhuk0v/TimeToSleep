package com.vadmax.io.domain.usercases

import com.vadmax.io.data.AppInfo
import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetSelectedApps {
    operator fun invoke(): Flow<List<AppInfo>>
}

class GetSelectedAppsImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : GetSelectedApps {

    override fun invoke() = settingsProvider.selectedAppsFlow
}

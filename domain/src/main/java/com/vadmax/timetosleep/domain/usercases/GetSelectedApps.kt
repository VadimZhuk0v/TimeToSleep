package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetSelectedApps {
    operator fun invoke(): Flow<List<AppInfo>>
}

internal class GetSelectedAppsImpl(
    private val settingsProvider: SettingsProvider,
) : GetSelectedApps {

    override fun invoke() = settingsProvider.selectedAppsFlow
}

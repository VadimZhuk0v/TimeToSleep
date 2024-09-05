package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetSelectedApps {
    operator fun invoke(): Flow<List<AppInfo>>
}

@Factory(binds = [GetSelectedApps::class])
internal class GetSelectedAppsImpl(private val settingsProvider: SettingsProvider) :
    GetSelectedApps {

    override fun invoke() = settingsProvider.selectedAppsFlow
}

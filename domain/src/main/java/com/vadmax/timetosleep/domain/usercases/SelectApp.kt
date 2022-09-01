package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SelectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

internal class SelectAppImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SelectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.selectApp(appInfo)
    }
}

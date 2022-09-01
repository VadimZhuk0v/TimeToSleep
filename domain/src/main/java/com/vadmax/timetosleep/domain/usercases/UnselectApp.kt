package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface UnselectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

internal class UnselectAppImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : UnselectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.removeApp(appInfo)
    }
}

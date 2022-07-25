package com.vadmax.io.domain.usercases

import com.vadmax.io.data.AppInfo
import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface UnselectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

class UnselectAppImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : UnselectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.removeApp(appInfo)
    }
}

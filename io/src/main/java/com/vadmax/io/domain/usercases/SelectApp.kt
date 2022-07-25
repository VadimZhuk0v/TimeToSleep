package com.vadmax.io.domain.usercases

import com.vadmax.io.data.AppInfo
import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SelectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

class SelectAppImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : SelectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.selectApp(appInfo)
    }
}

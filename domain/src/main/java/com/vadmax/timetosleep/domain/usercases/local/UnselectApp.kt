package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface UnselectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

@Factory(binds = [com.vadmax.timetosleep.domain.usercases.local.UnselectApp::class])
internal class UnselectAppImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : com.vadmax.timetosleep.domain.usercases.local.UnselectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.removeApp(appInfo)
    }
}

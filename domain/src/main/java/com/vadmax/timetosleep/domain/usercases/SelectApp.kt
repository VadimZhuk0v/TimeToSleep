package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SelectApp {
    suspend operator fun invoke(appInfo: AppInfo)
}

@Factory(binds = [SelectApp::class])
internal class SelectAppImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SelectApp {

    override suspend fun invoke(appInfo: AppInfo) = withContext(dispatcher) {
        settingsProvider.selectApp(appInfo)
    }
}

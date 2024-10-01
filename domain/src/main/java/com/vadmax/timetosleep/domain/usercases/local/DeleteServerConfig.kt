package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface DeleteServerConfig {

    suspend operator fun invoke()
}

@Factory(binds = [DeleteServerConfig::class])
internal class DeleteServerConfigImpl(
    private val settingsProvider: SettingsProvider,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : DeleteServerConfig {

    override suspend fun invoke() = withContext(coroutineContext) {
        settingsProvider.deleteServerConfig()
    }
}

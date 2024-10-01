package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.domain.data.ServerConfigDomainModel
import com.vadmax.timetosleep.domain.utils.toLocalModel
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetServerConfig {

    suspend operator fun invoke(value: String)
}

@Factory(binds = [SetServerConfig::class])
internal class SetServerConfigImpl(
    private val settingsProvider: SettingsProvider,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SetServerConfig {

    override suspend fun invoke(value: String) = withContext(coroutineContext) {
        val config = Json.decodeFromString<ServerConfigDomainModel>(value)
        settingsProvider.setServerDataModel(config.toLocalModel())
    }
}

package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.core.analytics.AppAnalytics
import com.vadmax.timetosleep.domain.data.ServerConfigDomainModel
import com.vadmax.timetosleep.domain.utils.toLocalModel
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetServerConfig {

    suspend operator fun invoke(value: String): Result<Unit>
}

@Factory(binds = [SetServerConfig::class])
internal class SetServerConfigImpl(
    private val settingsProvider: SettingsProvider,
    private val analytics: AppAnalytics,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SetServerConfig {

    override suspend fun invoke(value: String) = withContext(coroutineContext) {
        val config = try {
            Json.decodeFromString<ServerConfigDomainModel>(value)
        } catch (e: SerializationException) {
            analytics.scanUnsupportedQR()
            return@withContext Result.failure(e)
        }
        settingsProvider.setServerDataModel(config.toLocalModel())
        Result.success(Unit)
    }
}

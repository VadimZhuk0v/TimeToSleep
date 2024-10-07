package com.vadimax.timetosleep.remote.repositories

import com.vadimax.timetosleep.remote.data.TimeRemoteModel
import com.vadimax.timetosleep.remote.rcp.RPCClientFactory
import com.vadimax.timetosleep.remote.rcp.service.PCService
import com.vadmax.core.exceptions.NoPCConnectionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.rpc.streamScoped
import kotlinx.rpc.transport.ktor.client.KtorRPCClient
import kotlinx.rpc.withService
import org.koin.core.annotation.Single

interface PCApiRepository {

    val status: Flow<Unit>

    val serverTime: Flow<TimeRemoteModel>

    val enabled: Flow<Boolean>

    suspend fun subscribePingStatus()

    suspend fun collectServerTime()

    suspend fun sendTimeToServer(time: Flow<TimeRemoteModel>)

    suspend fun sendEnable(value: Boolean)

    suspend fun listenEnable()

    suspend fun turnOff()
}

@Single(binds = [PCApiRepository::class])
internal class PCApiRepositoryImpl(private val factory: RPCClientFactory) : PCApiRepository {

    override val status = MutableSharedFlow<Unit>()
    override val serverTime = MutableSharedFlow<TimeRemoteModel>()
    override val enabled = MutableSharedFlow<Boolean>(replay = 1)

    override suspend fun subscribePingStatus() {
        streamScoped {
            safeCall {
                status.emitAll(withService<PCService>().pingStatus())
            }
        }
    }

    override suspend fun collectServerTime() {
        streamScoped {
            safeCall {
                serverTime.emitAll(withService<PCService>().receiveTime())
            }
        }
    }

    override suspend fun sendTimeToServer(time: Flow<TimeRemoteModel>) {
        streamScoped {
            safeCall {
                withService<PCService>().shareTime(time)
            }
        }
    }

    override suspend fun sendEnable(value: Boolean) {
        safeCall { withService<PCService>().sendEnable(value) }
    }

    override suspend fun listenEnable() {
        streamScoped {
            safeCall {
                enabled.emitAll(withService<PCService>().listenEnable())
            }
        }
    }

    override suspend fun turnOff() {
        safeCall {
            withService<PCService>().turnOff()
        }
    }

    private suspend fun <T> safeCall(request: suspend KtorRPCClient.() -> T): T {
        val client = factory.getInstance() ?: throw NoPCConnectionException()
        return try {
            request(client)
        } catch (e: Exception) {
            factory.clear()
            throw e
        }
    }
}

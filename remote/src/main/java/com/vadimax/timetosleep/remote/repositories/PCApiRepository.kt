package com.vadimax.timetosleep.remote.repositories

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

    suspend fun subscribePingStatus()
}

@Single(binds = [PCApiRepository::class])
internal class PCApiRepositoryImpl(private val factory: RPCClientFactory) : PCApiRepository {

    override val status = MutableSharedFlow<Unit>()

    override suspend fun subscribePingStatus() {
        streamScoped {
            safeCall {
                status.emitAll(withService<PCService>().pingStatus())
            }
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

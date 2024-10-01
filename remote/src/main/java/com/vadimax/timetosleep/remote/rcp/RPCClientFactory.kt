package com.vadimax.timetosleep.remote.rcp

import com.vadimax.timetosleep.remote.usecases.GetServerConfig
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlinx.rpc.serialization.json
import kotlinx.rpc.transport.ktor.client.KtorRPCClient
import kotlinx.rpc.transport.ktor.client.rpc
import kotlinx.rpc.transport.ktor.client.rpcConfig
import org.koin.core.annotation.Single
import timber.log.Timber
import java.net.SocketTimeoutException

interface RPCClientFactory {

    suspend fun getInstance(): KtorRPCClient?

    fun clear()
}

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
@Single(binds = [RPCClientFactory::class])
internal class RPCClientFactoryImpl(
    private val coroutineScope: CoroutineScope,
    private val httpClient: HttpClient,
    private val getServerConfig: GetServerConfig,
) : RPCClientFactory {

    private var deferred: Deferred<KtorRPCClient?>? = null

    private val creationThread = newSingleThreadContext("Create RPC client thread")

    override fun clear() {
        deferred = null
    }

    override suspend fun getInstance(): KtorRPCClient? = withContext(creationThread) {
        val config = getServerConfig().firstOrNull() ?: run {
            Timber.w("⚠️ No server config")
            return@withContext null
        }
        if (deferred != null && deferred?.await() != null) {
            Timber.d("Return exciting RPC client")
            return@withContext deferred?.await()
        }
        Timber.d("Create new RPC client")
        deferred?.cancel()
        deferred = coroutineScope.async {
            try {
                httpClient.rpc {
                    url {
                        host = config.ipAddress
                        port = config.port
                    }
                    rpcConfig {
                        serialization {
                            json()
                        }
                    }
                }
            } catch (e: SocketTimeoutException) {
                Timber.w("⚠️ Failed to connect to the server")
                null
            }
        }
        return@withContext deferred?.await()
    }
}

package com.vadimax.timetosleep.remote.rcp

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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

@Single(binds = [RPCClientFactory::class])
internal class RPCClientFactoryImpl(
    private val coroutineScope: CoroutineScope,
    private val httpClient: HttpClient,
) : RPCClientFactory {

    private var deferred: Deferred<KtorRPCClient?>? = null

    override fun clear() {
        deferred = null
    }

    override suspend fun getInstance(): KtorRPCClient? {
        if (deferred != null && deferred?.await() != null) {
            Timber.d("Return exciting client")
            return deferred?.await()
        }
        Timber.d("Create new client")
        deferred?.cancel()
        deferred = coroutineScope.async {
            try {
                httpClient.rpc {
                    url {
                        host = "192.168.50.210"
                        port = 9006
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
        return deferred?.await()
    }
}

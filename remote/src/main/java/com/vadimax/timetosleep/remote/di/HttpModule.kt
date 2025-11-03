package com.vadimax.timetosleep.remote.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.rpc.krpc.ktor.client.installKrpc
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Module
internal class HttpModule {

    @Single
    fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
        installKrpc {
            connector {
                this.waitTimeout = 1.minutes
            }
        }
    }
}

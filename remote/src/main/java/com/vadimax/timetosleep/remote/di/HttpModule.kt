package com.vadimax.timetosleep.remote.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.rpc.transport.ktor.client.installRPC
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class HttpModule {

    @Single
    fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
        installRPC {
            waitForServices = true
        }
    }
}

package com.vadmax.timetosleep.domain.usercases.local

import com.vadimax.timetosleep.remote.usecases.GetServerConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

fun interface HasServerConfig {

    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [HasServerConfig::class])
internal class HasServerConfigImpl(private val getServerConfig: GetServerConfig) :
    HasServerConfig {

    override fun invoke() = getServerConfig().map { it != null }
}

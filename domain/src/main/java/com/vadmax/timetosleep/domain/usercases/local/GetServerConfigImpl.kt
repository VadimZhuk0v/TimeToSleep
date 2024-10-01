package com.vadmax.timetosleep.domain.usercases.local

import com.vadimax.timetosleep.remote.usecases.GetServerConfig
import com.vadmax.timetosleep.domain.utils.toRemoteModel
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory(binds = [GetServerConfig::class])
internal class GetServerConfigImpl(private val settingsProvider: SettingsProvider) :
    GetServerConfig {
    override fun invoke() = settingsProvider.getServerDataModel().map { it?.toRemoteModel() }
}

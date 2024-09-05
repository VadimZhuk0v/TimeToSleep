package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface IsDisableWifiEnable {
    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [IsDisableWifiEnable::class])
internal class IsDisableWifiEnableImpl(private val settingsProvider: SettingsProvider) :
    IsDisableWifiEnable {

    override fun invoke() = settingsProvider.isDisableWifiEnable
}

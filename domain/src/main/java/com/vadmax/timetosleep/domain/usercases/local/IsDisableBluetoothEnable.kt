package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface IsDisableBluetoothEnable {
    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [IsDisableBluetoothEnable::class])
internal class IsDisableBluetoothEnableImpl(private val settingsProvider: SettingsProvider) :
    IsDisableBluetoothEnable {

    override fun invoke() = settingsProvider.isDisableBluetoothEnable
}

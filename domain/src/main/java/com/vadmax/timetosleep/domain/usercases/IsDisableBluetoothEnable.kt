package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsDisableBluetoothEnable {
    operator fun invoke(): Flow<Boolean>
}

internal class IsDisableBluetoothEnableImpl(
    private val settingsProvider: SettingsProvider,
) : IsDisableBluetoothEnable {

    override fun invoke() = settingsProvider.isDisableBluetoothEnable
}

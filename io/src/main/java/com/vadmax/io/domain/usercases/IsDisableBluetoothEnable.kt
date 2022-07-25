package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsDisableBluetoothEnable {
    operator fun invoke(): Flow<Boolean>
}

class IsDisableBluetoothEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsDisableBluetoothEnable {

    override fun invoke() = settingsProvider.isDisableBluetoothEnable
}

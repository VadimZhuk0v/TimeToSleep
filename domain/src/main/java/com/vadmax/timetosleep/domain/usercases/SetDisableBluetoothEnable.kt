package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetDisableBluetoothEnable {
    suspend operator fun invoke(value: Boolean)
}

internal class SetDisableBluetoothEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetDisableBluetoothEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setDisableBluetoothEnable(value)
    }
}

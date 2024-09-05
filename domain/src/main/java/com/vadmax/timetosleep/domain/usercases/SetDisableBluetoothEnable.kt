package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetDisableBluetoothEnable {
    suspend operator fun invoke(value: Boolean)
}

@Factory(binds = [SetDisableBluetoothEnable::class])
internal class SetDisableBluetoothEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetDisableBluetoothEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setDisableBluetoothEnable(value)
    }
}

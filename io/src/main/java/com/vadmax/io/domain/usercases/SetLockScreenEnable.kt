package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SetLockScreenEnable {
    suspend operator fun invoke(value: Boolean)
}

class SetLockScreenEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : SetLockScreenEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setLockScreenEnable(value)
    }
}

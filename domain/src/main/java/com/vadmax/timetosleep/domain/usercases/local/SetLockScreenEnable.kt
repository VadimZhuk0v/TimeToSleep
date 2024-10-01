package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetLockScreenEnable {
    suspend operator fun invoke(value: Boolean)
}

@Factory(binds = [SetLockScreenEnable::class])
internal class SetLockScreenEnableImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetLockScreenEnable {

    override suspend fun invoke(value: Boolean) = withContext(dispatcher) {
        settingsProvider.setLockScreenEnable(value)
    }
}

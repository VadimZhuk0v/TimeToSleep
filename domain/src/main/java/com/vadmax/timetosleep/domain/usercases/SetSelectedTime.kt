package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SetSelectedTime {
    suspend operator fun invoke(time: Long?)
}

@Factory(binds = [SetSelectedTime::class])
internal class SetSelectedTimeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : SetSelectedTime {

    override suspend fun invoke(time: Long?) = withContext(dispatcher) {
        settingsProvider.setSelectedTime(time)
    }
}

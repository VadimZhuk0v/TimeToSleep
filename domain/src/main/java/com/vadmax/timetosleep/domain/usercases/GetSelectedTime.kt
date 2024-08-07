package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun interface GetSelectedTime {
    suspend operator fun invoke(): Long?
}

internal class GetSelectedTimeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : GetSelectedTime {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.getSelectedTime()
    }
}

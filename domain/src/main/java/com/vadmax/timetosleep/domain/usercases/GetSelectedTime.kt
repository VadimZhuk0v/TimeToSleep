package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface GetSelectedTime {
    suspend operator fun invoke(): Long?
}

internal class GetSelectedTimeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) :
    GetSelectedTime {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.getSelectedTime()
    }
}

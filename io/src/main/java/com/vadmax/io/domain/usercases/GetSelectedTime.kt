package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface GetSelectedTime {
    suspend operator fun invoke(): Long?
}

class GetSelectedTimeImpl internal constructor(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) :
    GetSelectedTime {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.getSelectedTime()
    }
}

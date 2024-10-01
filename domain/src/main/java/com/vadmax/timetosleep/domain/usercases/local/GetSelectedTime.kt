package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface GetSelectedTime {
    suspend operator fun invoke(): Long?
}

@Factory(binds = [com.vadmax.timetosleep.domain.usercases.local.GetSelectedTime::class])
internal class GetSelectedTimeImpl(
    private val settingsProvider: SettingsProvider,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : com.vadmax.timetosleep.domain.usercases.local.GetSelectedTime {

    override suspend fun invoke() = withContext(dispatcher) {
        settingsProvider.getSelectedTime()
    }
}

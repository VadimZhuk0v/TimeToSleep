package com.vadmax.timetosleep.domain.usercases.remote

import com.vadimax.timetosleep.remote.repositories.CommandApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface ShutdownRemote {

    suspend operator fun invoke()
}

@Factory(binds = [ShutdownRemote::class])
internal class ShutdownRemoteImpl(
    private val commandApiRepository: CommandApiRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : ShutdownRemote {
    override suspend fun invoke() = withContext(coroutineContext) {
        commandApiRepository.shutdown()
    }
}

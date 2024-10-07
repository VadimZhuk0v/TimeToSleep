package com.vadmax.timetosleep.domain.usercases.remote.request

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface TurnOffPC {

    suspend operator fun invoke()
}

@Factory(binds = [TurnOffPC::class])
internal class TurnOffPCImpl(
    private val pcApiRepository: PCApiRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : TurnOffPC {

    override suspend fun invoke() = withContext(coroutineContext) {
        pcApiRepository.turnOff()
    }
}

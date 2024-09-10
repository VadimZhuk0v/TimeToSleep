package com.vadmax.timetosleep.domain.usercases.remote.request

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SendTimerEnable {

    suspend operator fun invoke(value: Boolean)
}

@Factory(binds = [SendTimerEnable::class])
internal class SendTimerEnableImpl(
    private val pcApiRepository: PCApiRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SendTimerEnable {
    override suspend fun invoke(value: Boolean) = withContext(coroutineContext) {
        pcApiRepository.sendEnable(value)
    }
}

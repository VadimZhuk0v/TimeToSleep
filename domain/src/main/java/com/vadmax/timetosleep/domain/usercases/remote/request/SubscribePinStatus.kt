package com.vadmax.timetosleep.domain.usercases.remote.request

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

fun interface SubscribePinStatus {

    suspend operator fun invoke()
}

@Factory(binds = [SubscribePinStatus::class])
internal class SubscribePinStatusImpl(
    private val pcApiRepository: PCApiRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SubscribePinStatus {

    override suspend fun invoke() = withContext(coroutineContext) {
        Timber.i("🌐 Subscribe to ping status")
        pcApiRepository.subscribePingStatus()
    }
}

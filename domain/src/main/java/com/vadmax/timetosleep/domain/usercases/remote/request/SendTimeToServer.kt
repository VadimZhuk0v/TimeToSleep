package com.vadmax.timetosleep.domain.usercases.remote.request

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.domain.utils.toRemoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import kotlin.coroutines.CoroutineContext

fun interface SendTimeToServer {

    suspend operator fun invoke(time: Flow<TimeDomainModel>)
}

@Factory(binds = [SendTimeToServer::class])
internal class SendTimeToServerImpl(
    private val pcApiRepository: PCApiRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : SendTimeToServer {
    override suspend fun invoke(time: Flow<TimeDomainModel>) = withContext(coroutineContext) {
        pcApiRepository.sendTimeToServer(time.map { it.toRemoteModel() })
    }
}

package com.vadmax.timetosleep.domain.usercases.remote.data

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetPingStatus {

    operator fun invoke(): Flow<Unit>
}

@Factory(binds = [GetPingStatus::class])
internal class GetPingStatusImpl(private val pcApiRepository: PCApiRepository) : GetPingStatus {
    override fun invoke() = pcApiRepository.status
}

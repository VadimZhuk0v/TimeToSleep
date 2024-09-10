package com.vadmax.timetosleep.domain.usercases.remote.data

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.domain.utils.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

interface GetPCTime {

    operator fun invoke(): Flow<TimeDomainModel>
}

@Factory(binds = [GetPCTime::class])
internal class GetPCTimeImpl(private val pcApiRepository: PCApiRepository) : GetPCTime {
    override fun invoke() =
        pcApiRepository.serverTime.distinctUntilChanged().map { it.toDomainModel() }
}

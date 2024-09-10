package com.vadmax.timetosleep.domain.usercases.remote.data

import com.vadimax.timetosleep.remote.repositories.PCApiRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetPCTimerEnabled {

    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [GetPCTimerEnabled::class])
internal class GetPCTimerEnabledImpl(private val pcApiRepository: PCApiRepository) :
    GetPCTimerEnabled {

    override fun invoke() = pcApiRepository.enabled
}

package com.vadimax.timetosleep.remote.rcp.service

import com.vadimax.timetosleep.remote.data.TimeRemoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc

@Rpc
interface PCService {

    fun pingStatus(): Flow<Unit>

    fun receiveTime(): Flow<TimeRemoteModel>

    suspend fun shareTime(time: Flow<TimeRemoteModel>)

    suspend fun sendEnable(value: Boolean)

    fun listenEnable(): Flow<Boolean>

    suspend fun turnOff()
}

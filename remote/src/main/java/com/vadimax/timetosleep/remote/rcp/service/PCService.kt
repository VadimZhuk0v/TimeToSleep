package com.vadimax.timetosleep.remote.rcp.service

import com.vadimax.timetosleep.remote.data.TimeRemoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RPC

interface PCService : RPC {

    suspend fun pingStatus(): Flow<Unit>

    suspend fun receiveTime(): Flow<TimeRemoteModel>

    suspend fun shareTime(time: Flow<TimeRemoteModel>)

    suspend fun sendEnable(value: Boolean)

    suspend fun listenEnable(): Flow<Boolean>

    suspend fun turnOff()
}

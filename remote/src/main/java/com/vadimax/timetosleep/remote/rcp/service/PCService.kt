package com.vadimax.timetosleep.remote.rcp.service

import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.RPC

interface PCService : RPC {

    suspend fun pingStatus(): Flow<Unit>
}

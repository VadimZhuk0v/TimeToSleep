package com.vadimax.timetosleep.remote.usecases

import com.vadimax.timetosleep.remote.data.ServerConfigRemoteModel
import kotlinx.coroutines.flow.Flow

fun interface GetServerConfig {

    operator fun invoke(): Flow<ServerConfigRemoteModel?>
}

package com.vadmax.timetosleep.domain.utils

import com.vadimax.timetosleep.remote.data.DeviceRemoteModel
import com.vadimax.timetosleep.remote.data.ServerConfigRemoteModel
import com.vadimax.timetosleep.remote.data.TimeRemoteModel
import com.vadmax.timetosleep.domain.data.DeviceDomainModel
import com.vadmax.timetosleep.domain.data.ServerConfigDomainModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.local.data.ServerConfigLocalModel

internal fun TimeRemoteModel.toDomainModel() = TimeDomainModel(
    hours = hours,
    minutes = minutes,
)

internal fun TimeDomainModel.toRemoteModel() = TimeRemoteModel(
    hours = hours,
    minutes = minutes,
)

internal fun DeviceRemoteModel.toDomainModel() = DeviceDomainModel(
    ipAddress = ipAddress,
)

internal fun ServerConfigLocalModel.toRemoteModel() = ServerConfigRemoteModel(
    ipAddress = ipAddress,
    port = port,
)

internal fun ServerConfigDomainModel.toLocalModel() = ServerConfigLocalModel(
    ipAddress = ipAddress,
    port = port,
)

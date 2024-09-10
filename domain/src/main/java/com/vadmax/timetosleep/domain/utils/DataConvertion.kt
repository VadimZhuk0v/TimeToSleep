package com.vadmax.timetosleep.domain.utils

import com.vadimax.timetosleep.remote.data.TimeRemoteModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel

internal fun TimeRemoteModel.toDomainModel() = TimeDomainModel(hours, minutes)

internal fun TimeDomainModel.toRemoteModel() = TimeRemoteModel(hours, minutes)

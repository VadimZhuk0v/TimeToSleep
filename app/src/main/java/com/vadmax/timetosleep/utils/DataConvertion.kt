package com.vadmax.timetosleep.utils

import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel

fun TimeDomainModel.toUIModel() = TimeUIModel(hours, minutes)

fun TimeUIModel.toDomainModel() = TimeDomainModel(hours, minutes)

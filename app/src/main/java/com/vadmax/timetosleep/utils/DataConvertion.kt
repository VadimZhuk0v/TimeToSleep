package com.vadmax.timetosleep.utils

import com.vadmax.core.analytics.AnalyticsRingerMode
import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel

fun TimeDomainModel.toUIModel() = TimeUIModel(hours, minutes)

fun TimeUIModel.toDomainModel() = TimeDomainModel(hours, minutes)

fun RingerMode.toAnalytic() = when (this) {
    RingerMode.NORMAL -> AnalyticsRingerMode.NORMAL
    RingerMode.SILENT -> AnalyticsRingerMode.SILENT
    RingerMode.VIBRATE -> AnalyticsRingerMode.VIBRATE
}

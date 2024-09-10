package com.vadimax.timetosleep.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class TimeRemoteModel(val hours: Int, val minutes: Int)

package com.vadmax.timetosleep.domain.data

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfigDomainModel(val ipAddress: String, val port: Int)

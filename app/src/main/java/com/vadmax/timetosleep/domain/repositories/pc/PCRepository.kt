package com.vadmax.timetosleep.domain.repositories.pc

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface PCRepository {

    val connected: StateFlow<Boolean>

    fun attachScope(coroutineScope: CoroutineScope)
}

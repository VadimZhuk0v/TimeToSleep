package com.vadmax.timetosleep.domain.repositories.pc

sealed interface PCTimerRepositoryEvent {

    data object UnsupportedQR : PCTimerRepositoryEvent
}

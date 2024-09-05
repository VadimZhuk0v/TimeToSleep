package com.vadmax.timetosleep.ui.screens.home

import androidx.lifecycle.ViewModel
import com.vadmax.timetosleep.domain.repositories.pc.PCRepository
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenEvent
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(private val pcRepository: PCRepository) : ViewModel() {

    private val _event = MutableEventFlow<HomeScreenEvent>()
    val event: MutableEventFlow<HomeScreenEvent> = _event

    val connected = pcRepository.connected
}

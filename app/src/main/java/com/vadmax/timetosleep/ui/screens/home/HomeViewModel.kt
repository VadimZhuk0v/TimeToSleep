package com.vadmax.timetosleep.ui.screens.home

import androidx.lifecycle.ViewModel
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel : ViewModel() {

    private val _event = Channel<HomeScreenEvent>()
    val event: Flow<HomeScreenEvent> = _event.receiveAsFlow()
}

package com.vadmax.timetosleep.ui.screens.home.support

import androidx.compose.runtime.Composable
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object HomeScreenScope

sealed interface HomeScreenEvent {
    data object NavigatePhone : HomeScreenEvent
    data object NavigatePC : HomeScreenEvent
}

context(_: HomeScreenScope)
@Composable
fun ListenScreenEvent(
    event: Flow<HomeScreenEvent>,
    navigatePhone: VoidCallback,
    navigatePC: VoidCallback,
) {
    SingleEventEffect(event) {
        when (it) {
            HomeScreenEvent.NavigatePC -> navigatePC()
            HomeScreenEvent.NavigatePhone -> navigatePhone()
        }
    }
}

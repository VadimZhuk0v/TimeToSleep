package com.vadmax.timetosleep.ui.screens.home.support

import androidx.compose.runtime.Composable
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object HomeScreenScope

sealed interface HomeScreenEvent

context(HomeScreenScope)
@Composable
fun ListenScreenEvent(event: Flow<HomeScreenEvent>) {
    SingleEventEffect(event) {
    }
}

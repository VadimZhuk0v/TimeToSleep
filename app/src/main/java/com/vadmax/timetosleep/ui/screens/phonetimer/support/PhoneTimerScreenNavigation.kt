package com.vadmax.timetosleep.ui.screens.phonetimer.support

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.vadmax.timetosleep.ui.screens.phonetimer.PhoneTimerScreen
import kotlinx.serialization.Serializable

@Serializable
data object PhoneTimerScreenArgs

fun NavController.navigateToPhoneTimer(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = PhoneTimerScreenArgs,
    ) {
        popUpTo(0)
        navOptionsBuilder()
    }
}

fun NavGraphBuilder.phoneTimerScreenComposable() {
    composable<PhoneTimerScreenArgs> {
        with(PhoneTimerScope) {
            PhoneTimerScreen()
        }
    }
}

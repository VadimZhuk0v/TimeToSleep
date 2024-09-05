package com.vadmax.timetosleep.ui.screens.pctimer.support

import androidx.compose.animation.with
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.vadmax.timetosleep.ui.screens.pctimer.PCTimerScreen
import kotlinx.serialization.Serializable

@Serializable
data object PCTimerScreenArgs

fun NavController.navigateToPCTimer(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = PCTimerScreenArgs,
    ) {
        popUpTo(0)
        navOptionsBuilder()
    }
}

fun NavGraphBuilder.pcTimerScreenComposable() {
    composable<PCTimerScreenArgs> {
        with(PCTimerScreenScope) {
            PCTimerScreen()
        }
    }
}

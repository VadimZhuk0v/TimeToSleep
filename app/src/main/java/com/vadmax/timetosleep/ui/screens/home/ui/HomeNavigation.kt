package com.vadmax.timetosleep.ui.screens.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope
import com.vadmax.timetosleep.ui.screens.pctimer.support.pcTimerScreenComposable
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenArgs
import com.vadmax.timetosleep.ui.screens.phonetimer.support.phoneTimerScreenComposable

context(HomeScreenScope)
@Composable
fun HomeNavigation(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = PhoneTimerScreenArgs,
    ) {
        phoneTimerScreenComposable()
        pcTimerScreenComposable()
    }
}

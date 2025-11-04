package com.vadmax.timetosleep

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenArgs
import com.vadmax.timetosleep.ui.screens.home.support.homeScreenComposable

@Composable
fun ComposeApp() {
    AppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeScreenArgs,
        ) {
            homeScreenComposable()
        }
    }
}

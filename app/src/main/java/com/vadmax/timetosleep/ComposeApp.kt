package com.vadmax.timetosleep

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.ui.screens.home.HomeScreen
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreen
import com.vadmax.timetosleep.utils.LocalNavController

@Composable
fun ComposeApp() {
    AppTheme {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = HomeScreen.ROUTE,
            ) {
                composable(route = HomeScreen.ROUTE) {
                    HomeScreen()
                }
            }
        }
    }
}

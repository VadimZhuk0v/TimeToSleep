package com.vadmax.timetosleep.ui.screens.home.support

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.vadmax.timetosleep.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenArgs

fun NavController.navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = HomeScreenArgs,
    ) {
        launchSingleTop = true
        navOptionsBuilder()
    }
}

fun NavGraphBuilder.homeScreenComposable() {
    composable<HomeScreenArgs> {
        with(HomeScreenScope) {
            HomeScreen()
        }
    }
}

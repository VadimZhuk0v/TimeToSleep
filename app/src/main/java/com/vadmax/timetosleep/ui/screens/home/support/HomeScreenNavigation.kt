package com.vadmax.timetosleep.ui.screens.home.support

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

object HomeScreen {
    const val ROUTE = "home"
}

fun NavController.navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(HomeScreen.ROUTE) {
        launchSingleTop = true
        navOptionsBuilder()
    }
}
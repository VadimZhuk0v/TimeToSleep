package com.vadmax.timetosleep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.coreui.theme.TimeToSleepTheme
import com.vadmax.timetosleep.ui.screens.home.HomeScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeToSleepTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HomeScreen.destination) {
                    composable(HomeScreen.destination) {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}

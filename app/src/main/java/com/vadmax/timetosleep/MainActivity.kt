package com.vadmax.timetosleep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vadmax.timetosleep.ui.screens.home.HomeScreen
import com.vadmax.timetosleep.ui.theme.TimeToSleepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeToSleepTheme {
                HomeScreen()
            }
        }
    }
}

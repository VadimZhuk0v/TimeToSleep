package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import java.util.Date

@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    Scaffold {
        val isTimeEnable by viewModel.timerEnable.observeAsState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            Switch(
                checked = isTimeEnable ?: false,
                onCheckedChange = {
                    viewModel.setTimerEnable(it)
                },
            )
            Spacer(modifier = Modifier.height(50.dp))
            NumberClock(Date(viewModel.timeSelected.value!!)) { hour, minutes ->
                Timber.d("Selected $hour:$minutes")
                viewModel.setTime(hour, minutes)
            }
        }
    }
}

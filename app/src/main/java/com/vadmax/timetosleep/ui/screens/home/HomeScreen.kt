package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.theme.icon
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import java.util.Date

@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    Scaffold() {
        var initialSelectedTime by remember { mutableStateOf<Date?>(null) }
        LaunchedEffect(Unit) {
            viewModel.getInitialTime()?.let {
                initialSelectedTime = Date(it)
            }
        }
        initialSelectedTime ?: run {
            return@Scaffold
        }
        Box(
            Modifier
                .background(Color.Black)
        ) {

            Box(
                Modifier
                    .padding(16.dp)
                    .background(Color.Black)
            ) {
                val isTimeEnable by viewModel.timerEnable.observeAsState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Box(Modifier.size(300.dp)) {
                        Moon(isTimeEnable ?: false) {
                            viewModel.setTimerEnable(it)
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    NumberClock(initialSelectedTime) { hour, minutes ->
                        Timber.d("Selected $hour:$minutes")
                        viewModel.setTime(hour, minutes)
                    }
                }
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Settings",
                            tint = MaterialTheme.colors.icon,
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun Moon(isTimerEnable: Boolean, onCheckedChanged: (value: Boolean) -> Unit) {
    // TODO: Smooth stop animation
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lt_moon))
    val coroutineScope = rememberCoroutineScope()
    val lottieState = animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isTimerEnable,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        composition = lottieComposition,
        progress = lottieState.progress,
        modifier = Modifier.clickable(
            remember { MutableInteractionSource() },
            indication = null,
        ) {
            coroutineScope.launch {
                (lottieState as LottieAnimatable).snapTo(
                    composition = lottieComposition,
                    progress = 0F
                )
            }
            onCheckedChanged(isTimerEnable.not())
        }
    )
}

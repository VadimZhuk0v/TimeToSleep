package com.vadmax.timetosleep.ui.screens.phonetimer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.extensions.clickableNoRipple

@Composable
fun Moon(
    isTimerEnable: Boolean,
    onCheckedChange: (value: Boolean) -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lt_moon))
    val lottieState = animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isTimerEnable,
        iterations = LottieConstants.IterateForever,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish,
    )
    LottieAnimation(
        composition = composition,
        progress = { lottieState.progress },
        modifier = Modifier
            .fillMaxSize()
            .clickableNoRipple {
                onCheckedChange(isTimerEnable.not())
            },
    )
}

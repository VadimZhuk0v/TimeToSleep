package com.vadmax.timetosleep.ui.screens.pctimer.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenScope

context(PCTimerScreenScope)
@Composable
fun SandWatch() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lt_sand_watch))
    val lottieState = animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish,
        speed = 0.6F,
    )
    LottieAnimation(
        composition = composition,
        progress = { lottieState.progress },
        modifier = Modifier.size(400.dp),
    )
}

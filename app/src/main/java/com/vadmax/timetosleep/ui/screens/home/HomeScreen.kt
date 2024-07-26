package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.extensions.clickableNoRipple
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.ui.dialogs.settings.SettingsDialog
import com.vadmax.timetosleep.ui.screens.home.support.ListenHomeScreenEvent
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurface
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurfaceState
import com.vadmax.timetosleep.utils.extentions.requestIgnoreBatteryOptimizations
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    var initialTime by remember { mutableStateOf<Date?>(null) }
    LaunchedEffect(Unit) {
        val time = viewModel.getInitialTime() ?: Date().time
        initialTime = Date(time)
    }
    val isVibrationEnable by viewModel.vibrationEnable.collectAsState()
    val isTimerEnable by viewModel.timerEnable.collectAsState()

    val settingsDialogVisible = remember { mutableStateOf(false) }

    AnimatedContent(
        targetState = initialTime,
        label = "InitialTime",
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
        },
    ) {
        if (it == null) {
            Box(Modifier.fillMaxSize())
            return@AnimatedContent
        }
        val context = LocalContext.current
        HomeScreenContent(
            isTimerEnable = isTimerEnable,
            isVibrationEnable = isVibrationEnable,
            setTimerEnable = {
                if (context.requestIgnoreBatteryOptimizations().not()) {
                    viewModel.setTimerEnable(it)
                }
            },
            setTime = viewModel::setTime,
            initialTime = it,
            onSettingsClick = { settingsDialogVisible.value = true },
        )
    }

    SettingsDialog(visible = settingsDialogVisible)
    ListenHomeScreenEvent(viewModel.event)
}

@Composable
fun HomeScreenContent(
    isTimerEnable: Boolean,
    isVibrationEnable: Boolean,
    onSettingsClick: VoidCallback,
    initialTime: Date,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (hours: Int, minute: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val numberClockState = rememberNumberClockState(initialTime)
    val setTimeUpdated by rememberUpdatedState(setTime)
    Scaffold {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier.height(100.dp),
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = isTimerEnable.not(),
                        label = "Title",
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Text(
                            text = stringResource(R.string.home_tap_on_me),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8F)
                        .aspectRatio(1F),
                ) {
                    val state = remember { RippleSurfaceState() }
                    RippleSurface(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                    )
                    Moon(
                        isTimerEnable = isTimerEnable,
                        onCheckedChange = {
                            setTimerEnable(it)
                            coroutineScope.launch {
                                state.show()
                            }
                        },
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.margin4x))
                NumberClock(
                    isVibrationEnable = isVibrationEnable,
                    numberClockState = numberClockState,
                )
            }
            IconButton(
                modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 8.dp,
                    )
                    .navigationBarsPadding(),
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                onClick = onSettingsClick,
            )
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { numberClockState.time }
            .collectLatest {
                setTimeUpdated(it.hour, it.minute)
            }
    }
}

@Composable
private fun Moon(
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

@Preview
@Composable
private fun HomePreview() {
    AppTheme {
        HomeScreenContent(
            isTimerEnable = true,
            isVibrationEnable = true,
            onSettingsClick = { },
            setTimerEnable = {},
            initialTime = Date(),
            setTime = { _, _ -> },
        )
    }
}

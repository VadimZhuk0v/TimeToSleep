package com.vadmax.timetosleep.ui.screens.home

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.dialogs.settings.SettingsDialog
import com.vadmax.timetosleep.ui.theme.Dimens
import com.vadmax.timetosleep.ui.theme.screenBackground
import com.vadmax.timetosleep.ui.widgets.ad.heaser.HeaderAd
import com.vadmax.timetosleep.ui.widgets.ad.interstitial.intAd
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClockState
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.utils.constants.AppConstants
import java.util.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

object HomeScreen {
    const val destination = "home"
}

fun NavController.navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(HomeScreen.destination) {
        launchSingleTop = true
        navOptionsBuilder()
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = getViewModel()) {
    var initialSelectedTime by remember { mutableStateOf<Date?>(null) }
    LaunchedEffect(Unit) {
        val time = viewModel.getInitialTime() ?: Date().time
        initialSelectedTime = Date(time)
    }
    val initialTime = initialSelectedTime ?: run {
        return
    }

    val numberClockState = rememberNumberClockState(initialTime)
    val coroutineScope = rememberCoroutineScope()
    val time by numberClockState.time
    val isVibrationEnable by viewModel.vibrationEnable.collectAsState(initial = false)
    val enableTimerCounter by viewModel.enableTimerCounter.collectAsState(initial = 1)
    val isTimerEnable by viewModel.timerEnable.collectAsState(initial = false)
    val settingsDialogState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(time) {
        Timber.d("Selected time: ${time.hour}:${time.minute}")
        viewModel.setTime(time.hour, time.minute)
    }
    ShowInterstitialAds(enableTimerCounter)
    HomeScreenContent(
        coroutineScope = coroutineScope,
        isTimerEnable = isTimerEnable,
        settingsDialogState = settingsDialogState,
        numberClockState = numberClockState,
        isVibrationEnable = isVibrationEnable,
        setTimerEnable = viewModel::setTimerEnable
    )
}

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    coroutineScope: CoroutineScope,
    isTimerEnable: Boolean,
    settingsDialogState: ModalBottomSheetState,
    numberClockState: NumberClockState,
    isVibrationEnable: Boolean,
    setTimerEnable: (value: Boolean) -> Unit,
) {
    Scaffold {
        Box(Modifier.background(MaterialTheme.colors.screenBackground)) {
            Box {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HeaderAd()
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        AnimatedVisibility(visible = isTimerEnable.not()) {
                            Text(
                                text = stringResource(R.string.home_tap_on_me),
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Box(Modifier.size(300.dp)) {
                            Moon(isTimerEnable, setTimerEnable)
                        }
                        Spacer(modifier = Modifier.height(Dimens.margin4x))
                        NumberClock(
                            isVibrationEnable = isVibrationEnable,
                            numberClockState = numberClockState,
                        )
                    }
                }
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    onClick = {
                        coroutineScope.launch {
                            settingsDialogState.show()
                        }
                    },
                )
            }
            BottomDialog(settingsDialogState)
        }
    }
}

@SuppressWarnings("ForbiddenComment")
@Composable
private fun Moon(isTimerEnable: Boolean, onCheckedChanged: (value: Boolean) -> Unit) {
    // TODO: Smooth stop animation
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lt_moon))
    val lottieState = animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isTimerEnable,
        iterations = LottieConstants.IterateForever,
    )
    LaunchedEffect(isTimerEnable) {
        if (isTimerEnable.not()) {
            (lottieState as LottieAnimatable).snapTo(
                composition = lottieComposition,
                progress = 0F
            )
        }
    }
    LottieAnimation(
        composition = lottieComposition,
        progress = lottieState.progress,
        modifier = Modifier.clickable(
            remember { MutableInteractionSource() },
            indication = null,
        ) {
            onCheckedChanged(isTimerEnable.not())
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomDialog(sheetState: ModalBottomSheetState) {
    SettingsDialog(sheetState = sheetState)
}

@Composable
private fun ShowInterstitialAds(count: Int) {
    val context = LocalContext.current
    LaunchedEffect(count) {
        if (count % AppConstants.SHOW_ADS_EACH_TIMES != 0) {
            return@LaunchedEffect
        }
        intAd?.show(context as Activity)
    }
}

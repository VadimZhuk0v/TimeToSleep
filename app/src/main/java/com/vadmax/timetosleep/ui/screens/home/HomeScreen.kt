package com.vadmax.timetosleep.ui.screens.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.screens.applications.navigateToApplications
import com.vadmax.timetosleep.ui.theme.Dimens
import com.vadmax.timetosleep.ui.theme.screenBackground
import com.vadmax.timetosleep.ui.widgets.dialog.BottomSheetDialog
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.utils.extentions.screenPadding
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import java.util.Calendar
import java.util.Date

object HomeScreen {
    const val destination = "home"
}

fun NavController.navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(HomeScreen.destination) {
        launchSingleTop = true
        navOptionsBuilder()
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = getViewModel()) {
    var initialSelectedTime by remember { mutableStateOf<Date?>(null) }
    LaunchedEffect(Unit) {
        val time = viewModel.getInitialTime() ?: Date().time
        initialSelectedTime = Date(time)
    }
    if (initialSelectedTime == null) {
        return
    }
    HomeScreenContent(navController, initialSelectedTime!!, viewModel)
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun HomeScreenContent(
    navController: NavController,
    initialSelectedTime: Date,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    val firstTime by viewModel.firstTime.observeAsState(true)
    val calendar = Calendar.getInstance().apply {
        time = initialSelectedTime
    }
    val numberClocState = rememberNumberClockState(
        initialHour = calendar.hour,
        initialMinute = calendar.minute
    )
    val time by numberClocState.time
    Scaffold {
        val coroutineScope = rememberCoroutineScope()

        val settingsDialogState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        Box(
            Modifier
                .background(MaterialTheme.colors.screenBackground)
        ) {

            Box(
                Modifier.screenPadding()
            ) {
                val isTimeEnable by viewModel.timerEnable.observeAsState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    if (firstTime) {
                        Text(text = "Tap on me to enable")
                    }
                    Box(Modifier.size(300.dp)) {
                        Moon(isTimeEnable ?: false) {
                            viewModel.setTimerEnable(it)
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    NumberClock(numberClocState)
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
        }
        BottomDialog(settingsDialogState) {
            coroutineScope.launch {
                settingsDialogState.hide()
            }
            navController.navigateToApplications()
        }
        LaunchedEffect(time) {
            Timber.d("Selected time: ${time.hour}:${time.minute}")
            viewModel.setTime(time.hour, time.minute)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomDialog(sheetState: ModalBottomSheetState, openAppsScreen: () -> Unit) {
    BottomSheetDialog(sheetState) {
        Box(modifier = Modifier.padding(Dimens.screenPadding)) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable { openAppsScreen() },
                ) {
                    Text(
                        text = "Open apps screen",
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                Divider()
                Text(text = "${stringResource(R.string.home_version)} ${BuildConfig.VERSION_NAME}")
            }
        }
    }
}

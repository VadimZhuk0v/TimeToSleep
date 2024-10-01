package com.vadmax.timetosleep.ui.screens.phonetimer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.ui.dialogs.settings.PhoneSettingsDialog
import com.vadmax.timetosleep.ui.screens.phonetimer.support.ListenScreenEvent
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScope
import com.vadmax.timetosleep.ui.screens.phonetimer.ui.Moon
import com.vadmax.timetosleep.ui.screens.phonetimer.ui.PhoneTimerScreenState
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurface
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurfaceState
import com.vadmax.timetosleep.utils.extentions.requestIgnoreBatteryOptimizations
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

context(PhoneTimerScope)
@Composable
fun PhoneTimerScreen(viewModel: PhoneTimerViewModel = koinViewModel()) {
    val context = LocalContext.current

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val isVibrationEnable by viewModel.vibrationEnable.collectAsStateWithLifecycle()
    val isTimerEnable by viewModel.timerEnable.collectAsStateWithLifecycle()

    val settingsDialogVisible = remember { mutableStateOf(false) }

    PhoneTimerContent(
        screenState = screenState,
        isTimerEnable = isTimerEnable,
        isVibrationEnable = isVibrationEnable,
        onSettingsClick = { settingsDialogVisible.value = true },
        setTimerEnable = {
            if (context.requestIgnoreBatteryOptimizations().not()) {
                viewModel.setTimerEnable(it)
            }
        },
        setTime = viewModel::setTime,
    )
    PhoneSettingsDialog(visible = settingsDialogVisible)
    ListenScreenEvent(viewModel.event)
}

context(PhoneTimerScope)
@Composable
fun PhoneTimerContent(
    screenState: PhoneTimerScreenState,
    isTimerEnable: Boolean,
    isVibrationEnable: Boolean,
    onSettingsClick: VoidCallback,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (TimeUIModel) -> Unit,
) {
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = screenState,
                label = "Screen state",
                contentAlignment = Alignment.Center,
            ) {
                when (it) {
                    PhoneTimerScreenState.Idle -> IdleScreenState()
                    is PhoneTimerScreenState.Timer -> TimerScreenState(
                        screenState = it,
                        isTimerEnable = isTimerEnable,
                        isVibrationEnable = isVibrationEnable,
                        setTimerEnable = setTimerEnable,
                        setTime = setTime,
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding(),
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                onClick = onSettingsClick,
            )
        }
    }
}

context(PhoneTimerScope)
@Composable
private fun IdleScreenState(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
    }
}

context(PhoneTimerScope)
@Composable
private fun TimerScreenState(
    screenState: PhoneTimerScreenState.Timer,
    isTimerEnable: Boolean,
    isVibrationEnable: Boolean,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (TimeUIModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val numberClockState = rememberNumberClockState(screenState.initialTime)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Spacer(40.dp)
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
            onChangeByUser = setTime,
        )
    }
}

@Preview
@Composable
private fun PhoneTimerIdlePreview() {
    with(PhoneTimerScope) {
        AppTheme {
            PhoneTimerContent(
                isTimerEnable = false,
                isVibrationEnable = false,
                setTimerEnable = {},
                setTime = { },
                screenState = PhoneTimerScreenState.Idle,
                onSettingsClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun PhoneTimerPreview() {
    with(PhoneTimerScope) {
        AppTheme {
            PhoneTimerContent(
                isTimerEnable = false,
                isVibrationEnable = false,
                setTimerEnable = {},
                setTime = { },
                screenState = PhoneTimerScreenState.Timer(TimeUIModel(0, 0)),
                onSettingsClick = {},
            )
        }
    }
}

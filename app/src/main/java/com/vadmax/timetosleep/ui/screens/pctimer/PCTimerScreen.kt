package com.vadmax.timetosleep.ui.screens.pctimer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenScope
import com.vadmax.timetosleep.ui.screens.pctimer.ui.ConnectionStatus
import com.vadmax.timetosleep.ui.screens.pctimer.ui.PCTimerScreenState
import com.vadmax.timetosleep.ui.screens.pctimer.ui.SandWatch
import com.vadmax.timetosleep.ui.screens.phonetimer.ui.Moon
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurface
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurfaceState
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

context(PCTimerScreenScope)
@Composable
fun PCTimerScreen(viewModel: PCTimerViewModel = koinViewModel()) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val isVibrationEnable by viewModel.vibrationEnable.collectAsStateWithLifecycle()
    val isTimerEnable by viewModel.timerEnable.collectAsStateWithLifecycle()
    val connected by viewModel.connected.collectAsStateWithLifecycle()

    PCTimerContent(
        isTimerEnable = isTimerEnable,
        connected = connected,
        isVibrationEnable = isVibrationEnable,
        setTimerEnable = viewModel::setTimerEnable,
        setTime = viewModel::setTime,
        screenState = screenState,
        selectTime = viewModel.selectTime,
    )
    LifecycleStartEffect(Unit) {
        viewModel.attachScope()
        onStopOrDispose { viewModel.detachScope() }
    }
}

context(PCTimerScreenScope)
@Composable
private fun PCTimerContent(
    screenState: PCTimerScreenState,
    isTimerEnable: Boolean,
    connected: Boolean,
    isVibrationEnable: Boolean,
    selectTime: Flow<TimeUIModel>,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (TimeUIModel) -> Unit,
) {
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(20.dp)
            ConnectionStatus(
                connected = connected,
            )
            Spacer(20.dp)
            AnimatedContent(
                targetState = screenState,
                label = "Screen state",
                transitionSpec = { scaleIn() togetherWith scaleOut() },
            ) {
                when (it) {
                    PCTimerScreenState.Idle -> IdleScreenStata()
                    is PCTimerScreenState.Timer -> TimerScreenState(
                        screenState = it,
                        isTimerEnable = isTimerEnable,
                        isVibrationEnable = isVibrationEnable,
                        setTimerEnable = setTimerEnable,
                        setTime = setTime,
                        selectTime = selectTime,
                    )
                }
            }
        }
    }
}

context(PCTimerScreenScope)
@Composable
private fun IdleScreenStata(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        SandWatch()
    }
}

context(PCTimerScreenScope)
@Composable
private fun TimerScreenState(
    screenState: PCTimerScreenState.Timer,
    isTimerEnable: Boolean,
    isVibrationEnable: Boolean,
    selectTime: Flow<TimeUIModel>,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (TimeUIModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val numberClockState = rememberNumberClockState(screenState.initialTime)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
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
            onChangeByUser = setTime,
        )
    }
    SingleEventEffect(selectTime) {
        numberClockState.animateToTime(coroutineScope, it)
    }
}

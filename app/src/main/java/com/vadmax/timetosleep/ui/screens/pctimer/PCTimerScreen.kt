package com.vadmax.timetosleep.ui.screens.pctimer

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenScope
import com.vadmax.timetosleep.ui.screens.pctimer.ui.Clock
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurface
import com.vadmax.timetosleep.ui.widgets.ripplesurface.RippleSurfaceState
import com.vadmax.timetosleep.utils.extentions.requestIgnoreBatteryOptimizations
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Date

context(PCTimerScreenScope)
@Composable
fun PCTimerScreen(viewModel: PCTimerViewModel = koinViewModel()) {
    val initialTime by viewModel.initialTime.collectAsState(initial = null)
    val isVibrationEnable by viewModel.vibrationEnable.collectAsState()
    val isTimerEnable by viewModel.timerEnable.collectAsState()

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
        PCTimerContent(
            isTimerEnable = isTimerEnable,
            isVibrationEnable = isVibrationEnable,
            setTimerEnable = {
                if (context.requestIgnoreBatteryOptimizations().not()) {
                    viewModel.setTimerEnable(it)
                }
            },
            setTime = viewModel::setTime,
            initialTime = it,
        )
    }
}

context(PCTimerScreenScope)
@Composable
private fun PCTimerContent(
    isTimerEnable: Boolean,
    isVibrationEnable: Boolean,
    initialTime: Date,
    setTimerEnable: (value: Boolean) -> Unit,
    setTime: (hours: Int, minute: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val numberClockState = rememberNumberClockState(initialTime)
    val setTimeUpdated by rememberUpdatedState(setTime)
    Scaffold {
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
                Clock(
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
    }
    LaunchedEffect(Unit) {
        snapshotFlow { numberClockState.time }
            .collectLatest {
                setTimeUpdated(it.hour, it.minute)
            }
    }
}

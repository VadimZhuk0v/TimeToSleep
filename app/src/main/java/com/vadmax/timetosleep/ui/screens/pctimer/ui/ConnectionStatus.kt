package com.vadmax.timetosleep.ui.screens.pctimer.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenScope

context(PCTimerScreenScope)
@Composable
fun ConnectionStatus(
    modifier: Modifier = Modifier,
    connected: Boolean,
) {
    val text = if (connected) {
        stringResource(
            R.string.pc_connected,
        )
    } else {
        stringResource(R.string.pc_disconnected)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Circle(connected = connected)
        Spacer(Dimens.margin)
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
    }
}

@Composable
private fun Circle(connected: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "Connection infinite transition",
    )

    val targetColor = if (connected) {
        Color.Green
    } else {
        Color.Red
    }
    val color by animateColorAsState(
        targetValue = targetColor,
        label = "Connection color",
        animationSpec = tween(1500),
    )
    Box(
        modifier = Modifier.size(25.dp),
        contentAlignment = Alignment.Center,
    ) {
        for (i in 0..3) {
            val scale by infiniteTransition.animateFloat(
                label = "Connection scale",
                initialValue = 0F,
                targetValue = 1F,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                    ),
                    initialStartOffset = StartOffset(i * 300),
                ),
            )
            val alpha by infiniteTransition.animateFloat(
                label = "Connection alpha",
                initialValue = 1F,
                targetValue = 0F,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                    ),
                    initialStartOffset = StartOffset(i * 300),
                ),
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .alpha(alpha)
                    .scale(scale)
                    .fillMaxSize()
                    .background(color, CircleShape),
            )
        }
    }
}

@Preview
@Composable
private fun ConnectionStatusPreview() {
    AppTheme {
        with(PCTimerScreenScope) {
            Column {
                ConnectionStatus(connected = true)
                Spacer(10.dp)
                ConnectionStatus(connected = false)
            }
        }
    }
}

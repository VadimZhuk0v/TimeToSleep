package com.vadmax.timetosleep.ui.widgets.ripplesurface

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RippleSurface(
    state: RippleSurfaceState,
    modifier: Modifier = Modifier,
    maxRadius: Dp = 300.dp,
    initialWidth: Dp = 8.dp,
) {
    val density = LocalDensity.current

    val data = remember {
        persistentListOf(
            RippleData(),
            RippleData(),
        )
    }
    val (maxRadiusPx, initialWidthPx) = with(density) {
        maxRadius.toPx() to initialWidth.toPx()
    }

    Canvas(modifier = modifier) {
        data.forEach {
            drawCircle(
                color = Color.White,
                radius = it.radius.value,
                center = center,
                style = Stroke(
                    width = it.width.value,
                ),
                alpha = it.alpha.value,
            )
        }
    }
    LaunchedEffect(Unit) {
        state.showEvent.collectLatest {
            data.forEach {
                it.radius.snapTo(0F)
                it.width.snapTo(initialWidthPx)
                it.alpha.snapTo(1F)
            }
            data.forEachIndexed { index, it ->
                launch {
                    it.radius.animateTo(
                        targetValue = maxRadiusPx,
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = EaseOut,
                        ),
                    )
                }
                launch {
                    it.width.animateTo(
                        targetValue = 0F,
                        animationSpec = tween(
                            durationMillis = 2000,
                            easing = EaseOut,
                        ),
                    )
                }
                launch {
                    it.alpha.animateTo(
                        targetValue = 0F,
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = 1700,
                        ),
                    )
                }
                if (index != data.lastIndex) {
                    delay(150)
                } else {
                    delay(250)
                }
            }
        }
    }
}

private class RippleData {
    val radius = Animatable(0F)
    val width = Animatable(0F)
    val alpha = Animatable(0F)
}

class RippleSurfaceState {
    val showEvent = MutableSharedFlow<Unit>()

    suspend fun show() {
        showEvent.emit(Unit)
    }
}

package com.vadmax.timetosleep.ui.widgets.wheelpicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.utils.extentions.vibrate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun WheelPicker(
    isVibrationEnable: Boolean,
    itemHeight: Dp,
    itemsCount: Int,
    scrollState: LazyListState,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val itemHeightPx = LocalDensity.current.run { itemHeight.toPx() }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.height(itemHeight * 3)) {
        Wheel(
            scrollState,
            itemHeight,
            itemsCount,
            horizontalAlignment,
            itemContent,
        )
        autoScrolling(scrollState, coroutineScope, itemHeightPx)
    }

    var selectedItem by remember { mutableStateOf(0) }
    if (selectedItem != scrollState.firstVisibleItemIndex && scrollState.firstVisibleItemScrollOffset < itemHeightPx / 2) {
        selectedItem = scrollState.firstVisibleItemIndex
        if (isVibrationEnable) {
            LocalContext.current.vibrate()
        }
    }
}

private fun autoScrolling(state: LazyListState, scope: CoroutineScope, itemHeight: Float) {
    if (state.isScrollInProgress.not()) {
        val index = if (state.firstVisibleItemScrollOffset < itemHeight / 2) {
            state.firstVisibleItemIndex
        } else {
            state.firstVisibleItemIndex + 1
        }
        scope.launch {
            state.animateScrollToItem(index = index)
        }
    }
}

@Composable
private fun Wheel(
    state: LazyListState,
    itemHeight: Dp,
    itemsCount: Int,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (index: Int) -> Unit
) {
    val itemHeightPx = LocalDensity.current.run { itemHeight.toPx() }
    LazyColumn(
        state = state,
        horizontalAlignment = horizontalAlignment,
        modifier = Modifier.width(60.dp),
        content = {
            item("header") {
                Box(modifier = Modifier.height(itemHeight))
            }
            (0 until itemsCount).forEach { index ->
                item(index) {
                    if (state.layoutInfo.totalItemsCount == 0) {
                        return@item
                    }
                    val offset =
                        state.layoutInfo.visibleItemsInfo.find { it.key == index }?.offset
                            ?: 0
                    val alpha: Float
                    val rotation: Float
                    when {
                        offset < itemHeightPx -> {
                            alpha = min(offset / itemHeightPx + 0.5F, 1F)
                            rotation = (1F - alpha) * 100
                        }
                        offset > itemHeightPx -> {
                            alpha = itemHeightPx / offset
                            rotation = -((1F - alpha) * 100)
                        }
                        else -> {
                            alpha = 1F
                            rotation = 0F
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(itemHeight)
                            .alpha(alpha = alpha)
                            .graphicsLayer {
                                this.rotationX = rotation * 1.2F
                            },
                    ) {
                        itemContent(index)
                    }
                }
            }
            item("footer") {
                Box(modifier = Modifier.height(itemHeight))
            }
        },
    )
}

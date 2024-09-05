package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.coreui.theme.clock
import com.vadmax.timetosleep.ui.widgets.wheelpicker.WheelPicker

private val itemHeight = 60.dp

@Composable
fun NumberClock(
    isVibrationEnable: Boolean,
    numberClockState: NumberClockState,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
        ProvideTextStyle(
            value = MaterialTheme.typography.clock,
        ) {
            Wheel(
                isVibrationEnable = isVibrationEnable,
                state = numberClockState.hourState,
                horizontalAlignment = Alignment.End,
                itemsCount = 24,
            )
            Text(
                text = " : ",
                color = Color.White,
            )
            Wheel(
                isVibrationEnable = isVibrationEnable,
                state = numberClockState.minuteState,
                horizontalAlignment = Alignment.Start,
                itemsCount = 60,
            )
        }
    }
}

@SuppressWarnings("MagicNumber")
@Composable
fun Wheel(
    isVibrationEnable: Boolean,
    state: LazyListState,
    horizontalAlignment: Alignment.Horizontal,
    itemsCount: Int,
) {
    WheelPicker(
        isVibrationEnable = isVibrationEnable,
        itemHeight = itemHeight,
        scrollState = state,
        itemsCount = itemsCount,
        horizontalAlignment = horizontalAlignment,
    ) { index ->
        val text =
            if (index < 10) {
                "0$index"
            } else {
                index.toString()
            }
        Text(
            text = text,
            color = Color.White,
        )
    }
}

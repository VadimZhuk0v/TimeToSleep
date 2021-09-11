package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.theme.clock
import com.vadmax.timetosleep.ui.widgets.wheelpicker.WheelPicker

private val itemHeight = 60.dp

@Composable
fun NumberClock(
    numberClockState: NumberClockState = rememberNumberClockState(
        initialHour = 0,
        initialMinute = 0,
    )
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
        ProvideTextStyle(
            value = MaterialTheme.typography.clock,
        ) {
            Wheel(
                state = numberClockState.hourState,
                horizontalAlignment = Alignment.End,
                itemsCount = 24,
            )
            Text(text = " : ")
            Wheel(
                state = numberClockState.minuteState,
                horizontalAlignment = Alignment.Start,
                itemsCount = 60,
            )
        }
    }
}

@Composable
fun Wheel(
    state: LazyListState,
    horizontalAlignment: Alignment.Horizontal,
    itemsCount: Int
) {
    WheelPicker(
        itemHeight = itemHeight,
        scrollState = state,
        itemsCount = itemsCount,
        horizontalAlignment = horizontalAlignment,
    ) { index ->
        val text = if (index < 10) {
            "0$index"
        } else {
            index.toString()
        }
        Text(text = text)
    }
}

package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vadmax.timetosleep.ui.widgets.wheelpicker.WheelPicker

private val fontSize = 40.sp
private val itemHeight = 60.dp

@Composable
fun NumberClock(
    numberClockState: NumberClockState = rememberNumberClockState(
        initialHour = 0,
        initialMinute = 0,
    )
) {
    ClockContent(
        stateHour = numberClockState.hourState,
        stateMinute = numberClockState.minuteState,
    )
}

@Composable
private fun ClockContent(
    stateHour: LazyListState,
    stateMinute: LazyListState,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ProvideTextStyle(
            value = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
            )
        ) {
            WheelPicker(
                itemHeight = itemHeight,
                scrollState = stateHour,
                itemsCount = 24,
            ) { index ->
                val text = if (index < 10) {
                    "0$index"
                } else {
                    index.toString()
                }
                Text(text = text)
            }
            Text(text = " : ")
            WheelPicker(
                itemHeight = itemHeight,
                scrollState = stateMinute,
                itemsCount = 60,
            ) { index ->
                val text = if (index < 10) {
                    "0$index"
                } else {
                    index.toString()
                }
                Text(text = text)
            }
        }
    }
}

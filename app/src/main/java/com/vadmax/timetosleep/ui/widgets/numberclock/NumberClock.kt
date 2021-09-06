package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vadmax.timetosleep.ui.widgets.wheelpicker.WheelPicker
import timber.log.Timber
import java.util.Calendar
import java.util.Date

private val fontSize = 40.sp
private val itemHeight = 60.dp

@Composable
fun NumberClock(
    initialTime: Date?,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val calendar = Calendar.getInstance().apply { time = initialTime ?: Date() }
    var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        ProvideTextStyle(
            value = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
            )
        ) {
            WheelPicker(
                itemHeight = itemHeight,
                state = rememberLazyListState(initialFirstVisibleItemIndex = selectedHour),
                itemsCount = 24,
                onItemSelected = {
                    Timber.d("Selected hour: $it")
                    selectedHour = it
                }
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
                state = rememberLazyListState(initialFirstVisibleItemIndex = selectedMinute),
                itemsCount = 60,
                onItemSelected = {
                    Timber.d("Selected minutes: $it")
                    selectedMinute = it
                }
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
    ListenTimeChanges(
        hour = selectedHour,
        minutes = selectedMinute,
        callback = onTimeSelected,
    )
}

@Composable
private fun ListenTimeChanges(hour: Int, minutes: Int, callback: (hour: Int, minute: Int) -> Unit) {
    LaunchedEffect(hour, minutes) {
        callback(hour, minutes)
    }
}

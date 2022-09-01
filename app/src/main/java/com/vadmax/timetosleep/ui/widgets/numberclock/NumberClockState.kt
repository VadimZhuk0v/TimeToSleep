package com.vadmax.timetosleep.ui.widgets.numberclock

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.vadmax.core.utils.extentions.hour
import com.vadmax.core.utils.extentions.minute
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClockState.Companion.Saver
import java.util.Calendar
import java.util.Date

@SuppressLint("ComposableNaming")
@Composable
fun rememberNumberClockState(initialHour: Int, initialMinute: Int): NumberClockState {
    return rememberSaveable(saver = Saver) {
        NumberClockState(
            LazyListState(initialHour),
            LazyListState(initialMinute),
        )
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun rememberNumberClockState(calendar: Calendar): NumberClockState {
    return rememberNumberClockState(initialHour = calendar.hour, initialMinute = calendar.minute)
}

@SuppressLint("ComposableNaming")
@Composable
fun rememberNumberClockState(time: Date): NumberClockState {
    return rememberNumberClockState(Calendar.getInstance().apply { this.time = time })
}

class NumberClockState constructor(
    val hourState: LazyListState,
    val minuteState: LazyListState,
) {

    val time
        get() = derivedStateOf {
            Time(hourState.firstVisibleItemIndex, minuteState.firstVisibleItemIndex)
        }

    data class Time(
        val hour: Int,
        val minute: Int,
    )

    companion object {
        val Saver: Saver<NumberClockState, *> = listSaver(
            save = {
                listOf(
                    it.hourState.firstVisibleItemIndex,
                    it.minuteState.firstVisibleItemIndex,
                )
            },
            restore = {
                NumberClockState(
                    LazyListState(it[0]),
                    LazyListState(it[1]),
                )
            },
        )
    }
}

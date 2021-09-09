package com.vadmax.timetosleep.ui.widgets.numberclock

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClockState.Companion.Saver

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
        val minute: Int
    )

    companion object {
        val Saver: Saver<NumberClockState, *> = listSaver(
            save = {
                listOf(
                    it.hourState.firstVisibleItemIndex,
                    it.minuteState.firstVisibleItemIndex
                )
            },
            restore = {
                NumberClockState(
                    LazyListState(it[0]),
                    LazyListState(it[1]),
                )
            }
        )
    }
}

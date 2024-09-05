package com.vadmax.timetosleep.ui.screens.phonetimer.support

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PhoneTimerScope

sealed interface PhoneTimerScreenEvent {
    data object RippleSound : PhoneTimerScreenEvent
}

context(PhoneTimerScope)
@Composable
fun ListenScreenEvent(event: Flow<PhoneTimerScreenEvent>) {
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.water_drop) }

    SingleEventEffect(event) {
        when (it) {
            PhoneTimerScreenEvent.RippleSound -> {
                mediaPlayer.start()
            }
        }
    }
}

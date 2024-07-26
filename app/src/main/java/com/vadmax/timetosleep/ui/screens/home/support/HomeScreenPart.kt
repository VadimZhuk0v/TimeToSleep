package com.vadmax.timetosleep.ui.screens.home.support

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

sealed interface HomeScreenEvent {
    data object RippleSound : HomeScreenEvent
}

@Composable
fun ListenHomeScreenEvent(event: Flow<HomeScreenEvent>) {
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.water_drop) }

    SingleEventEffect(event) {
        when (it) {
            HomeScreenEvent.RippleSound -> {
                mediaPlayer.start()
            }
        }
    }
}

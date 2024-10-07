package com.vadmax.timetosleep.ui.screens.pctimer.support

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PCTimerScreenScope

sealed interface PCTimerScreenEvent {
    data object UnsupportedQR : PCTimerScreenEvent
}

context(PCTimerScreenScope)
@Composable
fun ListenPCTimerScreenEvent(event: Flow<PCTimerScreenEvent>) {
    val context = LocalContext.current
    SingleEventEffect(event) {
        when (it) {
            PCTimerScreenEvent.UnsupportedQR -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.pc_unsupported_qr),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}

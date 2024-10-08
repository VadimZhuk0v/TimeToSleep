package com.vadmax.timetosleep.ui.screens.pctimer.support

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PCTimerScreenScope

sealed interface PCTimerScreenEvent {
    data object UnsupportedQR : PCTimerScreenEvent
}

sealed interface PCTimerScreenDialog {
    data object Info : PCTimerScreenDialog
    data object Settings : PCTimerScreenDialog
    data object TurnOff : PCTimerScreenDialog
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

context(PCTimerScreenScope)
@Composable
fun ListenPCTimerScreenDialog(
    event: Flow<PCTimerScreenDialog>,
    showInfoDialog: VoidCallback,
    showSettingsDialog: VoidCallback,
    showTurnOffDialog: VoidCallback,
) {
    SingleEventEffect(event) {
        when (it) {
            PCTimerScreenDialog.Info -> showInfoDialog()
            PCTimerScreenDialog.Settings -> showSettingsDialog()
            PCTimerScreenDialog.TurnOff -> showTurnOffDialog()
        }
    }
}

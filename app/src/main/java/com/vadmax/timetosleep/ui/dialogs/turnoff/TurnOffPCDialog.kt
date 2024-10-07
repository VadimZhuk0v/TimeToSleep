package com.vadmax.timetosleep.ui.dialogs.turnoff

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppColors
import com.vadmax.timetosleep.ui.widgets.localicon.LocalIcon
import timber.log.Timber

@Composable
fun TurnOffPCDialog(
    visible: MutableState<Boolean>,
    onConfirmClick: VoidCallback,
) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        visible.value = false
                        onConfirmClick()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppColors.warningRed,
                    ),
                ) {
                    Text(text = stringResource(R.string.turn_off_pc_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        Timber.d("👆 On cancel turn off pc click")
                        visible.value = false
                    },
                ) {
                    Text(text = stringResource(R.string.turn_off_pc_dialog_cancel))
                }
            },
            title = { Text(text = stringResource(R.string.turn_off_pc_dialog_title)) },
            text = { Text(text = stringResource(R.string.turn_off_pc_dialog_body)) },
            icon = {
                LocalIcon(
                    modifier = Modifier.size(24.dp),
                    id = R.drawable.ic_turn_off,
                    tint = Color.White,
                )
            },
        )
    }
}

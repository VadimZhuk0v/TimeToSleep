package com.vadmax.timetosleep.ui.widgets.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.vadmax.timetosleep.ui.theme.dialogBackground

@Composable
fun Dialog(
    showDialog: Boolean,
    properties: DialogProperties = DialogProperties(),
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints() {
        if (showDialog.not()) {
            return@BoxWithConstraints
        }
        androidx.compose.ui.window.Dialog(
            onDismissRequest = onDismiss,
            properties = properties,
        ) {
            Surface(shape = MaterialTheme.shapes.large) {
                Box(
                    modifier = Modifier
                        .width((maxWidth.value * 0.8).dp)
                        .defaultMinSize(minHeight = 40.dp)
                        .background(MaterialTheme.colors.dialogBackground)
                ) {
                    content()
                }
            }
        }
    }
}

package com.vadmax.timetosleep.ui.widgets.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.coreui.theme.dialogBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetDialog(state: ModalBottomSheetState, content: @Composable () -> Unit) {
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.9F),
        sheetContent = {
            Surface(shape = MaterialTheme.shapes.large) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 20.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.dialogBackground),
                ) {
                    content()
                }
            }
        },
    ) {
    }
}

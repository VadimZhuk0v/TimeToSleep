@file:OptIn(ExperimentalMaterial3Api::class)

package com.vadmax.timetosleep.ui.widgets.appbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.vadmax.timetosleep.coreui.theme.LocalAppThemeColors

@Composable
fun AppModalBottomSheet(
    visible: MutableState<Boolean>,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    content: @Composable ColumnScope.() -> Unit,
) {
    val containerColor = LocalAppThemeColors.current.dialogBackground
    if (visible.value) {
        ModalBottomSheet(
            onDismissRequest = {
                visible.value = false
            },
            sheetState = sheetState,
            containerColor = containerColor,
            windowInsets = WindowInsets.displayCutout,
            content = {
                content()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(containerColor)
                        .navigationBarsPadding(),
                )
            },
        )
    }
}
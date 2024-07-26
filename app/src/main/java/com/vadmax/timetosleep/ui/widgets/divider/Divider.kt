package com.vadmax.timetosleep.ui.widgets.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = Color.Black.copy(alpha = 0.5F),
    )
}

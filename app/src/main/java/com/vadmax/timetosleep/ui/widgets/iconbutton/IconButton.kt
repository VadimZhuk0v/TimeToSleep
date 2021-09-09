package com.vadmax.timetosleep.ui.widgets.iconbutton

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.vadmax.timetosleep.ui.theme.icon

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String = "",
    tint: Color? = null,
    onClick: () -> Unit
) {
    androidx.compose.material.IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = tint ?: MaterialTheme.colors.icon,
            )
        }
    )
}

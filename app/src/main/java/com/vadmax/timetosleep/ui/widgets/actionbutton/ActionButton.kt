package com.vadmax.timetosleep.ui.widgets.actionbutton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.coreui.Shapes
import com.vadmax.timetosleep.coreui.VoidCallback
import kotlinx.serialization.json.JsonNull.content

@Composable
fun ActionButton(
    onClick: VoidCallback,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = Color.White,
    ),
    borderColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        content = content,
        colors = colors,
        shape = Shapes.shape16,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        ),
    )
}

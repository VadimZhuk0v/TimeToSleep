@file:Suppress("unused")

package com.vadmax.timetosleep.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
)

val Colors.icon: Color get() = Color.White

val Colors.dialogBackground: Color get() = DialogBackground

val Colors.screenBackground: Color get() = Color.Black

val Colors.textFieldBackground: Color get() = TextFieldBackground

@Composable
fun TimeToSleepTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

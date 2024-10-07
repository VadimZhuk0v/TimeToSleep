@file:Suppress("unused")

package com.vadmax.timetosleep.coreui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color.White,
    secondary = Teal200,
    background = Color.Black,
)

data class AppThemeColors(
    val icon: Color,
    val dialogBackground: Color,
    val screenBackground: Color,
    val textFieldBackground: Color,
)

val darkColors = AppThemeColors(
    icon = Color.White,
    dialogBackground = DialogBackground,
    screenBackground = Color.Black,
    textFieldBackground = TextFieldBackground,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = AppTypography,
        shapes = Shapes,
        content = content,
    )
}

val LocalAppThemeColors = staticCompositionLocalOf { darkColors }

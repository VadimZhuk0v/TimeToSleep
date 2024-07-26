package com.vadmax.timetosleep.coreui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.vadmax.timetoseleep.coreui.R

private val defaultFontFamily =
    FontFamily(
        Font(R.font.baloo),
    )

val Typography.clock
    get() =
        TextStyle(
            fontSize = 40.sp,
        )

val AppTypography =
    Typography(
        bodyLarge = Typography().bodyLarge.copy(fontFamily = defaultFontFamily),
        bodyMedium = Typography().bodyMedium.copy(fontFamily = defaultFontFamily),
        bodySmall = Typography().bodySmall.copy(fontFamily = defaultFontFamily),
        displayLarge = Typography().displayLarge.copy(fontFamily = defaultFontFamily),
        displayMedium = Typography().displayMedium.copy(fontFamily = defaultFontFamily),
        displaySmall = Typography().displaySmall.copy(fontFamily = defaultFontFamily),
        headlineLarge = Typography().headlineLarge.copy(fontFamily = defaultFontFamily),
        headlineMedium = Typography().headlineMedium.copy(fontFamily = defaultFontFamily),
        headlineSmall = Typography().headlineSmall.copy(fontFamily = defaultFontFamily),
        labelLarge = Typography().labelLarge.copy(fontFamily = defaultFontFamily),
        labelMedium = Typography().labelMedium.copy(fontFamily = defaultFontFamily),
        labelSmall = Typography().labelSmall.copy(fontFamily = defaultFontFamily),
        titleLarge = Typography().titleLarge.copy(fontFamily = defaultFontFamily),
        titleMedium = Typography().titleMedium.copy(fontFamily = defaultFontFamily),
        titleSmall = Typography().titleSmall.copy(fontFamily = defaultFontFamily),
    )

package com.vadmax.timetosleep.coreui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vadmax.timetosleep.coreui.R

private val defaultFontFamily = FontFamily(
    Font(R.font.baloo),
)

val Typography.clock
    get() = TextStyle(
        fontSize = 40.sp,
    )

val Typography = Typography(
    defaultFontFamily = defaultFontFamily,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    h2 = TextStyle(
        fontSize = 60.sp,
        fontWeight = FontWeight.Light,
    ),
    h4 = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.Normal,
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
)

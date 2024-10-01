package com.vadmax.timetosleep.ui.widgets.localimage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun LocalImage(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painterResource(id),
        modifier = modifier,
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        contentScale = contentScale,
        alignment = alignment,
    )
}

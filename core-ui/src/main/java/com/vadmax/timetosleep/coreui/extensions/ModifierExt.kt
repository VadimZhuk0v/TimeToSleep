package com.vadmax.timetosleep.coreui.extensions // ktlint-disable filename

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.Dimens

fun Modifier.screenPadding() = this.padding(Dimens.screenPadding)

fun Modifier.clickableNoRipple(block: VoidCallback) = this.composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        block()
    }
}

fun Modifier.notClickable() = clickableNoRipple {}

@file:Suppress("ktlint:standard:filename")

package com.vadmax.timetosleep.coreui.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.vadmax.timetosleep.coreui.VoidCallback

fun Modifier.clickableNoRipple(block: VoidCallback) = this.composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        block()
    }
}

fun Modifier.notClickable() = clickableNoRipple {}

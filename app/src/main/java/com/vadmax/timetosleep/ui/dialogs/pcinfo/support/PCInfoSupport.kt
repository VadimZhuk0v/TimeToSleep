package com.vadmax.timetosleep.ui.dialogs.pcinfo.support

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.utils.extentions.openLink
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PCInfoScope

sealed interface PCInfoEvent {
    data class OpenLink(val link: String) : PCInfoEvent
}

context(PCInfoScope)
@Composable
fun ListenPCInfoEvent(event: Flow<PCInfoEvent>) {
    val context = LocalContext.current

    SingleEventEffect(event) {
        when (it) {
            is PCInfoEvent.OpenLink -> context.openLink(it.link)
        }
    }
}

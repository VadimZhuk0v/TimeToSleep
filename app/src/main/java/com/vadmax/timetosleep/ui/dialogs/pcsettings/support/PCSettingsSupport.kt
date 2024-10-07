package com.vadmax.timetosleep.ui.dialogs.pcsettings.support

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.utils.extentions.openLink
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PCSettingsScope

sealed interface PCSettingsEvent {
    data class OpenLink(val link: String) : PCSettingsEvent
}

context(PCSettingsScope)
@Composable
fun ListenPCSettingsEvent(event: Flow<PCSettingsEvent>) {
    val context = LocalContext.current
    SingleEventEffect(event) {
        when (it) {
            is PCSettingsEvent.OpenLink -> context.openLink(it.link)
        }
    }
}

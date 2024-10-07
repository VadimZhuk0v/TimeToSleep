package com.vadmax.timetosleep.ui.dialogs.phonesettings.support

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vadmax.timetosleep.utils.extentions.openLink
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow

data object PhoneSettingsScope

sealed interface PhoneSettingsEvent {
    data class OpenLink(val link: String) : PhoneSettingsEvent
}

context(PhoneSettingsScope)
@Composable
fun ListenPhoneSettingsEvent(event: Flow<PhoneSettingsEvent>) {
    val context = LocalContext.current
    SingleEventEffect(event) {
        when (it) {
            is PhoneSettingsEvent.OpenLink -> context.openLink(it.link)
        }
    }
}

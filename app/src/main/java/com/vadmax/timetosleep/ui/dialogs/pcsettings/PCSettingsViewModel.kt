package com.vadmax.timetosleep.ui.dialogs.pcsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.timetosleep.domain.usercases.local.DeleteServerConfig
import com.vadmax.timetosleep.domain.usercases.local.GetOpenSourceLink
import com.vadmax.timetosleep.ui.dialogs.pcsettings.support.PCSettingsEvent
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCSettingsViewModel(
    private val deleteServerConfig: DeleteServerConfig,
    private val getOpenSourceLink: GetOpenSourceLink,
) : ViewModel() {

    private val _event = MutableEventFlow<PCSettingsEvent>()
    val event: EventFlow<PCSettingsEvent> = _event

    fun onUnpairClick() {
        Timber.i("👆 On unpair click")
        viewModelScope.launch {
            deleteServerConfig()
        }
    }

    fun onOpenSourceClick() {
        Timber.i("👆 Open source click")
        _event.tryEmit(PCSettingsEvent.OpenLink(getOpenSourceLink()))
    }
}

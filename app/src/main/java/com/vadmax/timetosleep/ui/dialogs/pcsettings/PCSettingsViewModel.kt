package com.vadmax.timetosleep.ui.dialogs.pcsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.timetosleep.domain.usercases.local.DeleteServerConfig
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCSettingsViewModel(private val deleteServerConfig: DeleteServerConfig) : ViewModel() {

    fun onUnpairClick() {
        Timber.i("👆 On unpair click")
        viewModelScope.launch {
            deleteServerConfig()
        }
    }
}

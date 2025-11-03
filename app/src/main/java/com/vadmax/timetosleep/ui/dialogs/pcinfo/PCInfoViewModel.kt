package com.vadmax.timetosleep.ui.dialogs.pcinfo

import androidx.lifecycle.ViewModel
import com.vadmax.timetosleep.domain.usercases.local.GetPCDownloadLink
import com.vadmax.timetosleep.ui.dialogs.pcinfo.support.PCInfoEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCInfoViewModel(private val getPCDownloadLink: GetPCDownloadLink) : ViewModel() {

    private val _event = Channel<PCInfoEvent>()
    val event = _event.receiveAsFlow()

    fun onDownloadClick() {
        Timber.i("👆 On download click")
        _event.trySend(PCInfoEvent.OpenLink(getPCDownloadLink.invoke()))
    }
}

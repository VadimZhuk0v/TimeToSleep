package com.vadmax.timetosleep.ui.dialogs.pcinfo

import androidx.lifecycle.ViewModel
import com.vadmax.timetosleep.domain.usercases.local.GetPCDownloadLink
import com.vadmax.timetosleep.ui.dialogs.pcinfo.support.PCInfoEvent
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class PCInfoViewModel(private val getPCDownloadLink: GetPCDownloadLink) : ViewModel() {

    private val _event = MutableEventFlow<PCInfoEvent>()
    val event: EventFlow<PCInfoEvent> = _event

    fun onDownloadClick() {
        Timber.i("👆 On download click")
        _event.tryEmit(PCInfoEvent.OpenLink(getPCDownloadLink.invoke()))
    }
}

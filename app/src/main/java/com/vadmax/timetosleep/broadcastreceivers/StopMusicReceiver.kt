package com.vadmax.timetosleep.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vadmax.core.log
import com.vadmax.timetosleep.domain.usercases.ApplyActions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StopMusicReceiver : BroadcastReceiver(), KoinComponent {

    private val applyActions: ApplyActions by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        log.d("Stop music receiver active")
        GlobalScope.launch(Dispatchers.Main) {
            applyActions()
        }
    }
}

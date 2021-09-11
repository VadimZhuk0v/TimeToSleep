package com.vadmax.timetosleep.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vadmax.timetosleep.domain.usercases.ApplyActions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class StopMusicReceiver : BroadcastReceiver(), KoinComponent {

    private val applyActions: ApplyActions by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("Stop music receiver is run")
        GlobalScope.launch(Dispatchers.Main) {
            applyActions()
        }
    }
}

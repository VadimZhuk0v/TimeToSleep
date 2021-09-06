package com.vadmax.timetosleep.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import com.vadmax.io.domain.usercases.SetTimerEnable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StopMusicReceiver : BroadcastReceiver(), KoinComponent {

    private val setTimerEnable: SetTimerEnable by inject()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        val am = context.getSystemService(AudioManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val r = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build()
            am.requestAudioFocus(r)
        } else {
            am.requestAudioFocus({}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
        GlobalScope.launch(Dispatchers.IO) {
            setTimerEnable(false)
        }
    }
}

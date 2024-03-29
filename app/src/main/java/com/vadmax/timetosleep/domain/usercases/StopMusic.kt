package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import com.vadmax.core.log

fun interface StopMusic {
    operator fun invoke()
}

class StopMusicImpl(
    private val context: Context,
) : StopMusic {

    override fun invoke() {
        val am = context.getSystemService(AudioManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val r = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build()
            am.requestAudioFocus(r)
        } else {
            am.requestAudioFocus({}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
        log.i("Music stopped")
    }
}

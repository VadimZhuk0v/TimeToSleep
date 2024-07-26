package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import timber.log.Timber

fun interface StopMusic {
    operator fun invoke()
}

class StopMusicImpl(private val context: Context) : StopMusic {

    override fun invoke() {
        context.getSystemService(AudioManager::class.java).also {
            it.requestAudioFocus(AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build())
        }
        Timber.i("✅ Music stopped")
    }
}

package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import org.koin.core.annotation.Factory
import timber.log.Timber

fun interface StopMusic {
    operator fun invoke()
}

@Factory(binds = [StopMusic::class])
class StopMusicImpl(private val context: Context) : StopMusic {

    override fun invoke() {
        context.getSystemService(AudioManager::class.java).also {
            it.requestAudioFocus(AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build())
        }
        Timber.i("✅ Music stopped")
    }
}

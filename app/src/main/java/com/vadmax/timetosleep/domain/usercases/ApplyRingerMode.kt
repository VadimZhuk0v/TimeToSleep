package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.media.AudioManager
import com.vadmax.io.data.RingerMode
import com.vadmax.io.domain.usercases.GetRingerMode
import kotlinx.coroutines.flow.first

class ApplyRingerMode(
    private val context: Context,
    private val getRingerMode: GetRingerMode,
) {

    suspend operator fun invoke() {
        val mode = when (getRingerMode().first()) {
            RingerMode.NORMAL -> AudioManager.RINGER_MODE_NORMAL
            RingerMode.SILENT -> AudioManager.RINGER_MODE_SILENT
            RingerMode.VIBRATE -> AudioManager.RINGER_MODE_VIBRATE
            null -> null
        }
        mode ?: return

        context.getSystemService(AudioManager::class.java).ringerMode = mode
    }
}

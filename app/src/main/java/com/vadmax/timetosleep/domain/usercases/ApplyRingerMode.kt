package com.vadmax.timetosleep.domain.usercases

import android.content.Context
import android.media.AudioManager
import com.vadmax.core.data.RingerMode
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

interface ApplyRingerMode {
    suspend operator fun invoke()
}

class ApplyRingerModeImpl(
    private val context: Context,
    private val getRingerMode: GetRingerMode,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : ApplyRingerMode {

    override suspend fun invoke() = withContext(dispatcher) {
        val mode = when (getRingerMode().first()) {
            RingerMode.NORMAL -> AudioManager.RINGER_MODE_NORMAL
            RingerMode.SILENT -> AudioManager.RINGER_MODE_SILENT
            RingerMode.VIBRATE -> AudioManager.RINGER_MODE_VIBRATE
            null -> null
        }
        mode ?: return@withContext

        context.getSystemService(AudioManager::class.java).ringerMode = mode
    }
}

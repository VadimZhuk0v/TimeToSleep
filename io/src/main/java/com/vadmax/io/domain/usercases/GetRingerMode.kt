package com.vadmax.io.domain.usercases

import com.vadmax.io.data.RingerMode
import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetRingerMode {
    operator fun invoke(): Flow<RingerMode?>
}

class GetRingerModeImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : GetRingerMode {

    override fun invoke() = settingsProvider.ringerMode
}

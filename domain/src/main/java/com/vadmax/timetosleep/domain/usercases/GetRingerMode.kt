package com.vadmax.timetosleep.domain.usercases

import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface GetRingerMode {
    operator fun invoke(): Flow<RingerMode?>
}

internal class GetRingerModeImpl(
    private val settingsProvider: SettingsProvider,
) : GetRingerMode {

    override fun invoke() = settingsProvider.ringerMode
}

package com.vadmax.timetosleep.domain.usercases.local

import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface GetRingerMode {
    operator fun invoke(): Flow<RingerMode?>
}

@Factory(binds = [com.vadmax.timetosleep.domain.usercases.local.GetRingerMode::class])
internal class GetRingerModeImpl(private val settingsProvider: SettingsProvider) :
    com.vadmax.timetosleep.domain.usercases.local.GetRingerMode {

    override fun invoke() = settingsProvider.ringerMode
}

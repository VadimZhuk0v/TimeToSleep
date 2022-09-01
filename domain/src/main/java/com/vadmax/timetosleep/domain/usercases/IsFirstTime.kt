package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsFirstTime {
    operator fun invoke(): Flow<Boolean>
}

internal class IsFirstTimeImpl(
    private val settingsProvider: SettingsProvider,
) : IsFirstTime {

    override fun invoke() = settingsProvider.isFirstTime
}

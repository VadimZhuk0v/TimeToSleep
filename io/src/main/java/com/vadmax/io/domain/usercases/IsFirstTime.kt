package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsFirstTime {
    operator fun invoke(): Flow<Boolean>
}

class IsFirstTimeImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsFirstTime {

    override fun invoke() = settingsProvider.isFirstTime
}

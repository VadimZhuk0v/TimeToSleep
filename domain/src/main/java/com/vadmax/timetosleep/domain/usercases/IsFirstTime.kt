package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface IsFirstTime {
    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [IsFirstTime::class])
internal class IsFirstTimeImpl(private val settingsProvider: SettingsProvider) : IsFirstTime {

    override fun invoke() = settingsProvider.isFirstTime
}

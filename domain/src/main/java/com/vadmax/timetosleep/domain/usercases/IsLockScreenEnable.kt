package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

fun interface IsLockScreenEnable {
    operator fun invoke(): Flow<Boolean>
}

@Factory(binds = [IsLockScreenEnable::class])
internal class IsLockScreenEnableImpl(private val settingsProvider: SettingsProvider) :
    IsLockScreenEnable {

    override fun invoke() = settingsProvider.isLockScreenEnable
}

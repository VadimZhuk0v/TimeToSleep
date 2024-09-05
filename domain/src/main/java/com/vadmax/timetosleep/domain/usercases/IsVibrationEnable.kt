package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory

fun interface IsVibrationEnable {
    operator fun invoke(): StateFlow<Boolean>
}

@Factory(binds = [IsVibrationEnable::class])
internal class IsVibrationEnableImpl(private val settingsProvider: SettingsProvider) :
    IsVibrationEnable {

    override fun invoke() = settingsProvider.isVibrationEnable
}

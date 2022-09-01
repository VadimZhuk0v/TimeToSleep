package com.vadmax.timetosleep.domain.usercases

import com.vadmax.timetosleep.local.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsVibrationEnable {
    operator fun invoke(): Flow<Boolean>
}

internal class IsVibrationEnableImpl(
    private val settingsProvider: SettingsProvider,
) : IsVibrationEnable {

    override fun invoke() = settingsProvider.isVibrationEnable
}

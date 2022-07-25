package com.vadmax.io.domain.usercases

import com.vadmax.io.data.SettingsProvider
import kotlinx.coroutines.flow.Flow

fun interface IsVibrationEnable {
    operator fun invoke(): Flow<Boolean>
}

class IsVibrationEnableImpl internal constructor(
    private val settingsProvider: SettingsProvider,
) : IsVibrationEnable {

    override fun invoke() = settingsProvider.isVibrationEnable
}

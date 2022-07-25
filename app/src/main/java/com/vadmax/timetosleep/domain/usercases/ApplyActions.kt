package com.vadmax.timetosleep.domain.usercases

import com.vadmax.io.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.io.domain.usercases.IsLockScreenEnable
import com.vadmax.io.domain.usercases.SetTimerEnable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

fun interface ApplyActions {
    suspend operator fun invoke()
}

@SuppressWarnings("LongParameterList")
class ApplyActionsImpl internal constructor(
    private val setTimerEnable: SetTimerEnable,
    private val stopMusic: StopMusic,
    private val closeApps: CloseApps,
    private val lockScreen: LockScreen,
    private val isLockScreenEnable: IsLockScreenEnable,
    private val isDisableBluetoothEnable: IsDisableBluetoothEnable,
    private val disableBluetooth: DisableBluetooth,
    private val applyRingerMode: ApplyRingerMode,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ApplyActions {

    override suspend fun invoke() = withContext(dispatcher) {
        setTimerEnable(false)
        stopMusic()
        applyRingerMode()
        if (isLockScreenEnable().first()) {
            lockScreen()
        }
        if (isDisableBluetoothEnable().first()) {
            disableBluetooth()
        }
    }
}

package com.vadmax.timetosleep.domain.usercases

import com.vadmax.io.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.io.domain.usercases.IsLockScreenEnable
import com.vadmax.io.domain.usercases.SetTimerEnable
import kotlinx.coroutines.flow.first

@SuppressWarnings("LongParameterList")
class ApplyActions(
    private val setTimerEnable: SetTimerEnable,
    private val stopMusic: StopMusic,
    private val closeApps: CloseApps,
    private val lockScreen: LockScreen,
    private val isLockScreenEnable: IsLockScreenEnable,
    private val isDisableBluetoothEnable: IsDisableBluetoothEnable,
    private val disableBluetooth: DisableBluetooth,
    private val applyRingerMode: ApplyRingerMode,
) {

    suspend operator fun invoke() {
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

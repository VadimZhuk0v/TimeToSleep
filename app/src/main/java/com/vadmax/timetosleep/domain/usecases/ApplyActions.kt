package com.vadmax.timetosleep.domain.usecases

import com.vadmax.timetosleep.domain.usercases.local.IsDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.local.IsLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.local.SetTimerEnable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

fun interface ApplyActions {
    suspend operator fun invoke()
}

@SuppressWarnings("LongParameterList")
@Factory(binds = [ApplyActions::class])
class ApplyActionsImpl(
    private val setTimerEnable: com.vadmax.timetosleep.domain.usercases.local.SetTimerEnable,
    private val stopMusic: StopMusic,
    private val closeApps: CloseApps,
    private val lockScreen: LockScreen,
    private val isLockScreenEnable:
    com.vadmax.timetosleep.domain.usercases.local.IsLockScreenEnable,
    private val isDisableBluetoothEnable:
    com.vadmax.timetosleep.domain.usercases.local.IsDisableBluetoothEnable,
    private val disableBluetooth: DisableBluetooth,
    private val applyRingerMode: ApplyRingerMode,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
) : ApplyActions {

    override suspend fun invoke() = withContext(dispatcher) {
        setTimerEnable(false)
        stopMusic()
        applyRingerMode()
        if (isLockScreenEnable().first()) {
            Timber.i("✅ Screen is locked")
            lockScreen()
        }
        if (isDisableBluetoothEnable().first()) {
            Timber.i("✅ Bluetooth is disabled")
            disableBluetooth()
        }
    }
}

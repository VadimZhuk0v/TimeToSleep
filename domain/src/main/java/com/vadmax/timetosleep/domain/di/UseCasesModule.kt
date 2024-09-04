package com.vadmax.timetosleep.domain.di

import com.vadmax.timetosleep.domain.usercases.GetEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.GetEnableTimerCounterImpl
import com.vadmax.timetosleep.domain.usercases.GetRingerMode
import com.vadmax.timetosleep.domain.usercases.GetRingerModeImpl
import com.vadmax.timetosleep.domain.usercases.GetSelectedApps
import com.vadmax.timetosleep.domain.usercases.GetSelectedAppsImpl
import com.vadmax.timetosleep.domain.usercases.GetSelectedTime
import com.vadmax.timetosleep.domain.usercases.GetSelectedTimeImpl
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.GetSoundEffectEnableImpl
import com.vadmax.timetosleep.domain.usercases.IncEnableTimerCounter
import com.vadmax.timetosleep.domain.usercases.IncEnableTimerCounterImpl
import com.vadmax.timetosleep.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.IsDisableBluetoothEnableImpl
import com.vadmax.timetosleep.domain.usercases.IsDisableWifiEnable
import com.vadmax.timetosleep.domain.usercases.IsDisableWifiEnableImpl
import com.vadmax.timetosleep.domain.usercases.IsFirstTime
import com.vadmax.timetosleep.domain.usercases.IsFirstTimeImpl
import com.vadmax.timetosleep.domain.usercases.IsLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.IsLockScreenEnableImpl
import com.vadmax.timetosleep.domain.usercases.IsTimerEnable
import com.vadmax.timetosleep.domain.usercases.IsTimerEnableImpl
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnable
import com.vadmax.timetosleep.domain.usercases.IsVibrationEnableImpl
import com.vadmax.timetosleep.domain.usercases.SelectApp
import com.vadmax.timetosleep.domain.usercases.SelectAppImpl
import com.vadmax.timetosleep.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.timetosleep.domain.usercases.SetDisableBluetoothEnableImpl
import com.vadmax.timetosleep.domain.usercases.SetDisableWifiEnable
import com.vadmax.timetosleep.domain.usercases.SetDisableWifiEnableImpl
import com.vadmax.timetosleep.domain.usercases.SetLockScreenEnable
import com.vadmax.timetosleep.domain.usercases.SetLockScreenEnableImpl
import com.vadmax.timetosleep.domain.usercases.SetRingerMode
import com.vadmax.timetosleep.domain.usercases.SetRingerModeImpl
import com.vadmax.timetosleep.domain.usercases.SetSelectedTime
import com.vadmax.timetosleep.domain.usercases.SetSelectedTimeImpl
import com.vadmax.timetosleep.domain.usercases.SetSoundEffectEnable
import com.vadmax.timetosleep.domain.usercases.SetSoundEffectEnableImpl
import com.vadmax.timetosleep.domain.usercases.SetTimerEnable
import com.vadmax.timetosleep.domain.usercases.SetTimerEnableImpl
import com.vadmax.timetosleep.domain.usercases.SetVibrationEnable
import com.vadmax.timetosleep.domain.usercases.SetVibrationEnableImpl
import com.vadmax.timetosleep.domain.usercases.UnselectApp
import com.vadmax.timetosleep.domain.usercases.UnselectAppImpl
import com.vadmax.timetosleep.domain.usercases.remote.ShutdownRemote
import com.vadmax.timetosleep.domain.usercases.remote.ShutdownRemoteImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<IsTimerEnable> { IsTimerEnableImpl(get()) }
    factory<GetEnableTimerCounter> { GetEnableTimerCounterImpl(get()) }
    factory<IncEnableTimerCounter> { IncEnableTimerCounterImpl(get()) }
    factory<SetTimerEnable> { SetTimerEnableImpl(get()) }
    factory<SetSelectedTime> { SetSelectedTimeImpl(get()) }
    factory<SetLockScreenEnable> { SetLockScreenEnableImpl(get()) }
    factory<GetSelectedTime> { GetSelectedTimeImpl(get()) }
    factory<SelectApp> { SelectAppImpl(get()) }
    factory<UnselectApp> { UnselectAppImpl(get()) }
    factory<GetSelectedApps> { GetSelectedAppsImpl(get()) }
    factory<IsFirstTime> { IsFirstTimeImpl(get()) }
    factory<IsLockScreenEnable> { IsLockScreenEnableImpl(get()) }
    factory<IsDisableWifiEnable> { IsDisableWifiEnableImpl(get()) }
    factory<IsDisableBluetoothEnable> { IsDisableBluetoothEnableImpl(get()) }
    factory<SetDisableWifiEnable> { SetDisableWifiEnableImpl(get()) }
    factory<SetDisableBluetoothEnable> { SetDisableBluetoothEnableImpl(get()) }
    factory<GetRingerMode> { GetRingerModeImpl(get()) }
    factory<SetRingerMode> { SetRingerModeImpl(get()) }
    factory<SetVibrationEnable> { SetVibrationEnableImpl(get()) }
    factory<IsVibrationEnable> { IsVibrationEnableImpl(get()) }
    factory<SetSoundEffectEnable> { SetSoundEffectEnableImpl(get()) }
    factory<GetSoundEffectEnable> { GetSoundEffectEnableImpl(get()) }
    factory<ShutdownRemote> { ShutdownRemoteImpl(get()) }
}

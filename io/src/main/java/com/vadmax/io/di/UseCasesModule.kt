package com.vadmax.io.di

import com.vadmax.io.domain.usercases.GetEnableTimerCounter
import com.vadmax.io.domain.usercases.GetEnableTimerCounterImpl
import com.vadmax.io.domain.usercases.GetRingerMode
import com.vadmax.io.domain.usercases.GetRingerModeImpl
import com.vadmax.io.domain.usercases.GetSelectedApps
import com.vadmax.io.domain.usercases.GetSelectedAppsImpl
import com.vadmax.io.domain.usercases.GetSelectedTime
import com.vadmax.io.domain.usercases.GetSelectedTimeImpl
import com.vadmax.io.domain.usercases.IncEnableTimerCounter
import com.vadmax.io.domain.usercases.IncEnableTimerCounterImpl
import com.vadmax.io.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.io.domain.usercases.IsDisableBluetoothEnableImpl
import com.vadmax.io.domain.usercases.IsDisableWifiEnable
import com.vadmax.io.domain.usercases.IsDisableWifiEnableImpl
import com.vadmax.io.domain.usercases.IsFirstTime
import com.vadmax.io.domain.usercases.IsFirstTimeImpl
import com.vadmax.io.domain.usercases.IsLockScreenEnable
import com.vadmax.io.domain.usercases.IsLockScreenEnableImpl
import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.io.domain.usercases.IsTimerEnableImpl
import com.vadmax.io.domain.usercases.IsVibrationEnable
import com.vadmax.io.domain.usercases.IsVibrationEnableImpl
import com.vadmax.io.domain.usercases.SelectApp
import com.vadmax.io.domain.usercases.SelectAppImpl
import com.vadmax.io.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.io.domain.usercases.SetDisableBluetoothEnableImpl
import com.vadmax.io.domain.usercases.SetDisableWifiEnable
import com.vadmax.io.domain.usercases.SetDisableWifiEnableImpl
import com.vadmax.io.domain.usercases.SetLockScreenEnable
import com.vadmax.io.domain.usercases.SetLockScreenEnableImpl
import com.vadmax.io.domain.usercases.SetRingerMode
import com.vadmax.io.domain.usercases.SetRingerModeImpl
import com.vadmax.io.domain.usercases.SetSelectedTime
import com.vadmax.io.domain.usercases.SetSelectedTimeImpl
import com.vadmax.io.domain.usercases.SetTimerEnable
import com.vadmax.io.domain.usercases.SetTimerEnableImpl
import com.vadmax.io.domain.usercases.SetVibrationEnable
import com.vadmax.io.domain.usercases.SetVibrationEnableImpl
import com.vadmax.io.domain.usercases.UnselectApp
import com.vadmax.io.domain.usercases.UnselectAppImpl
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
}

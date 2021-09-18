package com.vadmax.io.di

import com.vadmax.io.domain.usercases.GetEnableTimerCounter
import com.vadmax.io.domain.usercases.GetRingerMode
import com.vadmax.io.domain.usercases.GetSelectedApps
import com.vadmax.io.domain.usercases.GetSelectedTime
import com.vadmax.io.domain.usercases.IncEnableTimerCounter
import com.vadmax.io.domain.usercases.IsDisableBluetoothEnable
import com.vadmax.io.domain.usercases.IsDisableWifiEnable
import com.vadmax.io.domain.usercases.IsFirstTime
import com.vadmax.io.domain.usercases.IsLockScreenEnable
import com.vadmax.io.domain.usercases.IsTimerEnable
import com.vadmax.io.domain.usercases.IsVibrationEnable
import com.vadmax.io.domain.usercases.SelectApp
import com.vadmax.io.domain.usercases.SetDisableBluetoothEnable
import com.vadmax.io.domain.usercases.SetDisableWifiEnable
import com.vadmax.io.domain.usercases.SetLockScreenEnable
import com.vadmax.io.domain.usercases.SetRingerMode
import com.vadmax.io.domain.usercases.SetSelectedTime
import com.vadmax.io.domain.usercases.SetTimerEnable
import com.vadmax.io.domain.usercases.SetVibrationEnable
import com.vadmax.io.domain.usercases.UnselectApp
import org.koin.dsl.module

val useCasesModule = module {
    single { IsTimerEnable(get()) }
    single { GetEnableTimerCounter(get()) }
    single { IncEnableTimerCounter(get()) }
    single { SetTimerEnable(get()) }
    single { SetSelectedTime(get()) }
    single { SetLockScreenEnable(get()) }
    single { GetSelectedTime(get()) }
    single { SelectApp(get()) }
    single { UnselectApp(get()) }
    single { GetSelectedApps(get()) }
    single { IsFirstTime(get()) }
    single { IsLockScreenEnable(get()) }
    single { IsDisableWifiEnable(get()) }
    single { IsDisableBluetoothEnable(get()) }
    single { SetDisableWifiEnable(get()) }
    single { SetDisableBluetoothEnable(get()) }
    single { GetRingerMode(get()) }
    single { SetRingerMode(get()) }
    single { SetVibrationEnable(get()) }
    single { IsVibrationEnable(get()) }
}

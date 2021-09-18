package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usercases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.ApplyRingerMode
import com.vadmax.timetosleep.domain.usercases.CloseApps
import com.vadmax.timetosleep.domain.usercases.DisableBluetooth
import com.vadmax.timetosleep.domain.usercases.DisableWifi
import com.vadmax.timetosleep.domain.usercases.GetAppsList
import com.vadmax.timetosleep.domain.usercases.LockScreen
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivation
import com.vadmax.timetosleep.domain.usercases.StopMusic
import org.koin.dsl.module

val useCasesModule = module {
    single { SetAlarmActivation(get(), get(), get()) }
    single { GetAppsList(get()) }
    single { CloseApps(get(), get()) }
    single { StopMusic(get()) }
    single { DisableBluetooth(get()) }
    single { DisableWifi(get()) }
    single { LockScreen(get()) }
    single { ApplyRingerMode(get(), get()) }
    single { ApplyActions(get(), get(), get(), get(), get(), get(), get(), get()) }
}

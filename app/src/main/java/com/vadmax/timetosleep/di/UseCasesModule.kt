package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usercases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.ApplyActionsImpl
import com.vadmax.timetosleep.domain.usercases.ApplyRingerMode
import com.vadmax.timetosleep.domain.usercases.ApplyRingerModeImpl
import com.vadmax.timetosleep.domain.usercases.CancelApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.CancelApplyActionsWorkerImpl
import com.vadmax.timetosleep.domain.usercases.CloseApps
import com.vadmax.timetosleep.domain.usercases.CloseAppsImpl
import com.vadmax.timetosleep.domain.usercases.DisableBluetooth
import com.vadmax.timetosleep.domain.usercases.DisableBluetoothImpl
import com.vadmax.timetosleep.domain.usercases.DisableWifi
import com.vadmax.timetosleep.domain.usercases.DisableWifiImpl
import com.vadmax.timetosleep.domain.usercases.GetAppsList
import com.vadmax.timetosleep.domain.usercases.GetAppsListImpl
import com.vadmax.timetosleep.domain.usercases.LockScreen
import com.vadmax.timetosleep.domain.usercases.LockScreenImpl
import com.vadmax.timetosleep.domain.usercases.ScheduleApplyActionsWorker
import com.vadmax.timetosleep.domain.usercases.ScheduleApplyActionsWorkerImpl
import com.vadmax.timetosleep.domain.usercases.StopMusic
import com.vadmax.timetosleep.domain.usercases.StopMusicImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetAppsList> { GetAppsListImpl(get()) }
    factory<CloseApps> { CloseAppsImpl(get(), get()) }
    factory<StopMusic> { StopMusicImpl(get()) }
    factory<DisableBluetooth> { DisableBluetoothImpl(get()) }
    factory<DisableWifi> { DisableWifiImpl(get()) }
    factory<LockScreen> { LockScreenImpl(get()) }
    factory<CancelApplyActionsWorker> { CancelApplyActionsWorkerImpl(get()) }
    factory<ApplyRingerMode> { ApplyRingerModeImpl(get(), get()) }
    factory<ApplyActions> {
        ApplyActionsImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory<ScheduleApplyActionsWorker> { ScheduleApplyActionsWorkerImpl(get(), get()) }
}

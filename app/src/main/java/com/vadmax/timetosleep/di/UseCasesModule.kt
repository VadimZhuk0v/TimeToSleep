package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usercases.ApplyActions
import com.vadmax.timetosleep.domain.usercases.ApplyActionsImpl
import com.vadmax.timetosleep.domain.usercases.ApplyRingerMode
import com.vadmax.timetosleep.domain.usercases.ApplyRingerModeImpl
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
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivation
import com.vadmax.timetosleep.domain.usercases.SetAlarmActivationImpl
import com.vadmax.timetosleep.domain.usercases.StopMusic
import com.vadmax.timetosleep.domain.usercases.StopMusicImpl
import org.koin.dsl.module

val useCasesModule = module {
    single<SetAlarmActivation> { SetAlarmActivationImpl(get(), get(), get()) }
    single<GetAppsList> { GetAppsListImpl(get()) }
    single<CloseApps> { CloseAppsImpl(get(), get()) }
    single<StopMusic> { StopMusicImpl(get()) }
    single<DisableBluetooth> { DisableBluetoothImpl() }
    single<DisableWifi> { DisableWifiImpl(get()) }
    single<LockScreen> { LockScreenImpl(get()) }
    single<ApplyRingerMode> { ApplyRingerModeImpl(get(), get()) }
    single<ApplyActions> {
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
}
